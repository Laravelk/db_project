package GUI.cargo;

import GUI.Controller.TripController;
import Server.DataBaseServer;
import java.text.ParseException;

public class CargoController {
    private CargoModel model;
    private CargoWindow view;

    public CargoController(DataBaseServer server, TripController tripController,
                           String dateIn, String dateOut) {
        model = new CargoModel(server);
        try {
            view = new CargoWindow(this, dateIn, dateOut);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        view.getKindField().addActionListener(actionEvent -> {
            model.getPrepareData().setKind(view.getKindField().getText());
            view.getCostField().setEnabled(true);
        });

        view.getCostField().addActionListener(actionEvent -> {
            int insurance = (int) (Integer.parseInt(view.getCostField().getText()) * 0.1);
            model.getPrepareData().setCost_insurance(insurance);
            view.getCountTextField().setEnabled(true);
        });

        view.getCountTextField().addActionListener(actionEvent -> {
            model.getPrepareData().setCount(Integer.parseInt(view.getCountTextField().getText()));
            view.getWeightTextField().setEnabled(true);
        });

        view.getWeightTextField().addActionListener(actionEvent -> {
            System.out.println("NEXT");
            model.getPrepareData().setWeight(Integer.parseInt(view.getWeightTextField().getText()));
            double insurance = Double.parseDouble(view.getCostField().getText()) * 0.1;
            view.getCostField().setText("Стоимость страховки " + String.valueOf
                    (insurance * model.getPrepareData().getCount()) + "$");
            view.getAddCargoButton().setEnabled(true);
            view.getNextButton().setEnabled(true);
        });

        view.getAddCargoButton().addActionListener(actionEvent -> {
            view.getContentPane().removeAll();
            model.getPrepareData().setDateIn(dateIn);
            model.getPrepareData().setDateOut(dateOut);
            model.insertPrepareData();
        });

        view.getNextButton().addActionListener(actionEvent -> {
            model.getPrepareData().setDateIn(dateIn);
            model.getPrepareData().setDateOut(dateOut);
            model.insertPrepareData();
            tripController.commitWindow(tripController.getWindowNumber());
            view.dispose();
        });

        view.getSkipButton().addActionListener(actionEvent -> {
            tripController.changeWindow(tripController.getWindowNumber() + 1);
            view.dispose();
        });

        view.getWarehouses().addActionListener(actionEvent -> {
            String id = (String) view.getWarehouses().getSelectedItem();
            model.getPrepareData().setWarehouseID(id);
        });

        view.getPrevButton().addActionListener(actionEvent -> {
            tripController.changeWindow(tripController.getWindowNumber() - 1);
            view.dispose();
        });

        view.setVisible(false);
    }

    public CargoModel getModel() {
        return model;
    }

    public void setVisible(boolean t) {
        view.setVisible(t);
    }
}