package GUI.requests.infoaboutwarehouse;

import Server.DataBaseServer;

public class InfoAboutWarehouseController {
    private InfoAboutWarehouseModel model;
    private InfoAboutWarehouseView view;
    public InfoAboutWarehouseController(DataBaseServer server) {
        model = new InfoAboutWarehouseModel(server);
        view = new InfoAboutWarehouseView(this);

        view.getWarehouses().addActionListener(actionEvent -> {
            updateInfo();
        });

        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
                updateInfo();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getDateOut().addActionListener(actionEvent -> {
            view.getWarehouses().setEnabled(true);
            updateInfo();
        });

        view.setVisible(true);
    }

    void updateInfo() {
        int idWarehouse = Integer.parseInt(view.getWarehouses().getItemAt(view.getWarehouses().getSelectedIndex()));
        String dateIn = view.getDateIn().getText();
        String dateOut = view.getDateOut().getText();
        DataAboutWarehouse warehouseData = model.getWarehouseData(idWarehouse, dateIn, dateOut);
        DataAboutFlightsWithWarehouse dataFlight = model.getFlights(idWarehouse, dateIn, dateOut);
        view.updateLabels(warehouseData, dataFlight);
    }

    public InfoAboutWarehouseModel getModel() {
        return model;
    }
}
