package GUI.requests.hotellist;

import Server.DataBaseServer;

import java.util.LinkedList;
import java.util.Vector;

public class HotelRequestModel {
    private DataBaseServer server;
    HotelRequestModel(DataBaseServer server) {
        this.server = server;
    }

    Vector<Vector<String>> getRequest(Vector<String> hotelNames, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.secondRequest(hotelNames, isOnlyWork, isOnlyTravel);
    }

    LinkedList<String> getHotelNames() {
        return server.getHotels();
    }
}
