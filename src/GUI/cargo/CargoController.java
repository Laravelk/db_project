package GUI.cargo;

import GUI.Controller.TripController;
import Server.DataBaseServer;
import java.text.ParseException;

public class CargoController {
    private final CargoModel model;
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
            System.out.println("KIND");
            view.getCostField().setEnabled(true);
        });

        view.getCostField().addActionListener(actionEvent -> {
            int insurance = (int) (Integer.parseInt(view.getCostField().getText()) * 0.1);
            model.getPrepareData().setCost_insurance(insurance);
            model.getPrepareData().setReal_wrap(Integer.parseInt(view.getCostField().getText()));
            view.getCountTextField().setEnabled(true);
        });

        view.getCountTextField().addActionListener(actionEvent -> {
            model.getPrepareData().setCount(Integer.parseInt(view.getCountTextField().getText()));
            view.getWeightTextField().setEnabled(true);
        });

        view.getWeightTextField().addActionListener(actionEvent -> {
            model.getPrepareData().setWeight(Integer.parseInt(view.getWeightTextField().getText()));
            double insurance = Double.parseDouble(view.getCostField().getText()) * 0.1;
            view.getCostField().setText("Стоимость страховки " + String.valueOf
                    (insurance * model.getPrepareData().getCount()) + "$");
            view.getVolumeTextField().setEnabled(true);
        });

        view.getVolumeTextField().addActionListener(actionEvent -> {
            model.getPrepareData().setVolume(Integer.parseInt(view.getVolumeTextField().getText()));
            view.getAddCargoButton().setEnabled(true);
            view.getNextButton().setEnabled(true);
        });


        view.getAddCargoButton().addActionListener(actionEvent -> {
            model.getPrepareData().setDateIn(dateIn);
            model.getPrepareData().setDateOut(dateOut);
            model.insertPrepareData();
            view.dispose();
            CargoController controller = new CargoController(server, tripController, dateIn, dateOut);
            controller.setVisible(true);
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
