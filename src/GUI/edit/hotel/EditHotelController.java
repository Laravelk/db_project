package GUI.edit.hotel;

import Server.DataBaseServer;

import javax.swing.*;

public class EditHotelController {
    private EditHotelModel model;
    private EditHotelView view;
    public EditHotelController(DataBaseServer server, int tripID, int clientID) {
        model = new EditHotelModel(server);
        view = new EditHotelView(this);

        view.getHotels().addActionListener(actionEvent -> {
            JComboBox box = (JComboBox)actionEvent.getSource();
            String name = (String) box.getSelectedItem();
            model.setHotelName(name);
            view.updatePrice();
            model.setPrice(Integer.parseInt(view.getPrice().getText()));
        });

        view.getOk().addActionListener(actionEvent -> {
            model.commit(tripID, clientID);
            view.dispose();
        });

        view.setVisible(true);
    }

    public EditHotelModel getModel() {
        return model;
    }
}
