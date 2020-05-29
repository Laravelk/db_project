package GUI.requests.infoabouthotel;

import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutHotelModel {
    private DataBaseServer server;
    InfoAboutHotelModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<Integer> getCount(String dateIn, String dateOut) {
        return server.getCountInHotelByTime(dateIn, dateOut);
    }

    public LinkedList<String> getHotelsName() {
        return server.getHotels();
    }
}
