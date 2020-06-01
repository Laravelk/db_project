package GUI.edit.hotel;

import Server.DataBaseServer;

import java.util.LinkedList;

public class EditHotelModel {
    private DataBaseServer server;
    private String hotelName;
    private int price;
    EditHotelModel(DataBaseServer server) {
        this.server = server;
    }

    LinkedList<String> getHotels() {
        return server.getHotels();
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getPrice() {
        return price;
    }

    public void commit(int idTrip, int clientID) {
        int hotelID = server.getIdHostel(hotelName);
        int bookingID = server.getBookingID(idTrip);
        server.updateBookingInfo(clientID, hotelID, bookingID, price);
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
