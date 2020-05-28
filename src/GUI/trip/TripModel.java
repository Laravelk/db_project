package GUI.trip;

import Data.TicketData;
import Data.TripData;
import Server.DataBaseServer;

import java.util.Vector;

public class TripModel {
    private DataBaseServer server;
    TripModel(DataBaseServer server) {
        this.server = server;
    }

    public Vector<Vector<String>> getTrips() {
        return server.getTrips();
    }

    void setNewTicket(int tripID, int clientID, TicketData ticket) {
        server.changeFlightClientsInformation(tripID, clientID, ticket);
    }

    TripData getTripDataByID(int tripID) {
        return server.getTripDataByID(tripID);
    }

    public Vector<Vector<String>> getClientsTrip(int id) {
        return server.getClientsTrip(id);
    }
}
