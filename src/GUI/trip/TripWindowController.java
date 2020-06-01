package GUI.trip;

import Data.TicketData;
import Data.TripData;
import GUI.edit.cargo.EditCargoController;
import GUI.edit.excursion.EditExcursionController;
import GUI.flight.FlightController;
import GUI.requests.infoabouttrip.InfoAboutController;
import Server.DataBaseServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TripWindowController {
    private DataBaseServer server;
    private TripModel model;
    private MainTripWindow mainTripWindow;
    private int clientID;
    private int selectionTrip;

    public TripWindowController(DataBaseServer server, boolean isFiltered, int id) {
        this.server = server;
        this.clientID = id;
        model = new TripModel(server);
        init(isFiltered, id);
    }

    void setSelectionId(int selectionId) {
        this.selectionTrip = selectionId;
    }

    private void init(boolean isFiltered, int id) {
            mainTripWindow = new MainTripWindow(isFiltered, id, this);

            mainTripWindow.getEditFlight().addActionListener(actionEvent -> {
                if (mainTripWindow.isTableSelection()) {
                    int selectionID = mainTripWindow.getSelectionID();
                    TripData tripData = model.getTripDataByID(selectionID);
                    int cargoWeight = server.getWeightCargoByTripId(tripData.getID());
                    FlightController controller = new FlightController(server,
                            this, convertDate(tripData.getDateIn()), convertDate(tripData.getDateOut()), cargoWeight);
                }
            });

            mainTripWindow.getGetInfoAboutTrip().addActionListener(actionEvent -> {
                if (mainTripWindow.isTableSelection()) {
                    int selectionID = mainTripWindow.getSelectionID();
                    InfoAboutController controller = new InfoAboutController(server, selectionID);
                }
            });

            mainTripWindow.getEditCargo().addActionListener(actionEvent -> {
                if (mainTripWindow.isTableSelection()) {
                    EditCargoController editCargoController = new EditCargoController(server, selectionTrip);
                }
            });

            mainTripWindow.getEditExcursion().addActionListener(actionEvent -> {
                int selectionID = mainTripWindow.getSelectionID();
                if (mainTripWindow.isTableSelection()) {
                    TripData tripData = model.getTripDataByID(selectionID);
                    EditExcursionController controller = new EditExcursionController(server, selectionTrip, tripData.getDateIn(),
                            tripData.getDateOut(), clientID);
                }
            });

            mainTripWindow.setVisible(true);
    }

    private String convertDate(String dateIn) {
        String str = dateIn.substring(0, 10);
        String []d = str.split("-");
        return d[2] + "." + d[1] + "." + d[0];
    }

    public void setNewTicket(TicketData ticket) {
        model.setNewTicket(selectionTrip, clientID, ticket);
    }

    public TripModel getModel() {
        return model;
    }
}