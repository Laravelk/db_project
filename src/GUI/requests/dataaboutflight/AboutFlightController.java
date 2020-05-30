package GUI.requests.dataaboutflight;

import Data.FlightData;
import Server.DataBaseServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutFlightController {
    private AboutFlightModel model;
    private AboutFlightView view;
    public AboutFlightController(DataBaseServer server) {
        model = new AboutFlightModel(server);
        view = new AboutFlightView(this);

        view.getDateIn().addActionListener(actionEvent -> {
            view.updateTable(view.getDateIn().getText());
            view.getGetInfo().setEnabled(true);
        });

        view.getGetInfo().addActionListener(actionEvent -> {
            if (view.isSelected()) {
                FlightData data = model.getFlightDataByID(view.getSelectedID());
                AboutFlightData aboutFlightData = model.getDataAboutFlight(view.getSelectedID(),
                        data.getAirplaneData().isCargoPlane());
                AboutFlightInfoDialog dialog = new AboutFlightInfoDialog(this, data, aboutFlightData);
                dialog.setVisible(true);
            }
        });

        view.getFlightTable().getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            view.setSelected(true);
            int selectionRow = listSelectionEvent.getLastIndex();
            view.setSelectedID(Integer.parseInt(view.getFlightTable().getModel().getValueAt
                    (selectionRow, 0).toString()));
        });
        view.setVisible(true);
    }

    public AboutFlightModel getModel() {
        return model;
    }
}
