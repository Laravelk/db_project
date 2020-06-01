package GUI.mainwindowforflight;

import Data.AirplaneData;
import GUI.Controller.TripController;
import GUI.requests.dataaboutflight.AboutFlightController;
import Server.DataBaseServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainControllerForFlight {
    private MainModelForFlight model;
    private MainWindowForFlight view;
    public MainControllerForFlight(DataBaseServer server) {
        model = new MainModelForFlight(server);
        view = new MainWindowForFlight(this);

        view.getGetDataAboutFlight().addActionListener(actionEvent -> {
            AboutFlightController controller = new AboutFlightController(server);
        });

        view.getRemoveButton().addActionListener(actionEvent -> {
            if (view.isTableSelection()) {
                int flightID = Integer.parseInt(String.valueOf(view.getFlightTables().
                        getModel().getValueAt(view.getFlightTables().getSelectedRow(), 0)));
                model.removeByID(flightID);
                view.updateTable();
            }
        });

        view.getAddPlane().addActionListener(actionEvent -> {
            AirplaneData data = new AirplaneData();
            AddAirplane addAirplane = new AddAirplane(model);
            data.setCargoPlane(false);

            addAirplane.getSeatCount().addActionListener(actionEvent15 -> {
                data.setSeatCount(Integer.parseInt(addAirplane.getSeatCount().getText()));
                addAirplane.getCargoWeight().setEnabled(true);
            });

            addAirplane.getCargoWeight().addActionListener(actionEvent16 -> {
                data.setCargoWeight(Integer.parseInt(addAirplane.getCargoWeight().getText()));
                addAirplane.getVolumeWeight().setEnabled(true);
            });

            addAirplane.getVolumeWeight().addActionListener(actionEvent17 -> {
                data.setVolumeWeight(Integer.parseInt(addAirplane.getVolumeWeight().getText()));
                addAirplane.getOk().setEnabled(true);
            });

            addAirplane.getCargoPlane().addActionListener(actionEvent14 -> {
                data.setCargoPlane(addAirplane.getCargoPlane().isSelected());
            });

            addAirplane.getOk().addActionListener(actionEvent18 -> {
                model.addAirplane(data);
                addAirplane.dispose();
            });

            addAirplane.setVisible(true);
        });

        view.getAddButton().addActionListener(actionEvent -> {
            AddFlight addFlight = new AddFlight(model);
            addFlight.setVisible(true);

            addFlight.getOk().addActionListener(actionEvent1 -> {
                model.commit(addFlight.getNewFlightData());
                addFlight.dispose();
            });

            addFlight.getDate().addActionListener(actionEvent12 -> {
                addFlight.getNewFlightData().setData(addFlight.getDate().getText());
                addFlight.getAir().setEnabled(true);
            });

            addFlight.getAir().addActionListener(actionEvent13 -> {
                addFlight.getNewFlightData().getAirplaneData().setID(Integer.parseInt((String) addFlight.getAir().getSelectedItem()));
            });

        });

        view.setVisible(true);
    }

    public MainModelForFlight getModel() {
        return model;
    }
}