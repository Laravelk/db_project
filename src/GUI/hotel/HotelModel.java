package GUI.hotel;

import Data.HotelBookingInfo;
import Server.DataBaseServer;

import java.util.LinkedList;

public class HotelModel {
    private DataBaseServer server;
    private HotelBookingInfo hotelInfo;

    HotelModel(DataBaseServer server) {
        this.server = server;
        hotelInfo = new HotelBookingInfo();
    }

    void setName(String name) {
        hotelInfo.setName(name);
    }

    void setPrice(int price) {
        hotelInfo.setPrice(price);
    }

    void setDateIn(String dataIn) {
        hotelInfo.setDateIn(dataIn);
    }

    void setDateOut(String dataOut) {
        hotelInfo.setDateOut(dataOut);
    }

    public HotelBookingInfo getHotelInfo() {
        return hotelInfo;
    }

    LinkedList<String> getHotels() {
        return server.getHotels();
    }

    void clear() {
        hotelInfo = null;
    }
}
