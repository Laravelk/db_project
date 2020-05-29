package GUI.requests.hotellist;

import Server.DataBaseServer;

import javax.swing.*;

public class HotelRequestController {
    private HotelRequestModel model;
    private HotelRequestView view;

    public HotelRequestController(DataBaseServer server) {
        model = new HotelRequestModel(server);
        view = new HotelRequestView(this);

        view.getCategoryFilter().getTravelCheck().addActionListener(actionEvent -> {
            view.setOnlyTravel(view.getCategoryFilter().getTravelCheck().isSelected());
            view.setOnlyWork(false);
            view.getCategoryFilter().getWorkCheck().setSelected(false);
            view.updateTable();
        });

        view.getCategoryFilter().getWorkCheck().addActionListener(actionEvent -> {
            view.setOnlyWork(view.getCategoryFilter().getWorkCheck().isSelected());
            view.setOnlyTravel(false);
            view.getCategoryFilter().getTravelCheck().setSelected(false);
            view.updateTable();
        });

        for (JCheckBox box : view.getHotelFilter().getCheckBoxes()) {
            box.addActionListener(actionEvent -> view.updateTable());
        }

        view.setVisible(true);
    }

    HotelRequestModel getModel() {
        return model;
    }
}
