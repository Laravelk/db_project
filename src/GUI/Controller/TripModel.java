package GUI.Controller;

import Data.*;
import Server.DataBaseServer;

import java.util.LinkedList;

public class TripModel {
    private LinkedList<ExcursionData> pickedExcursion = null;
    private HotelBookingInfo bookingInfo = null;
    private TicketData ticketData = null;
    private LinkedList<CargoData> cargoDataLinkedList = null;
    private ClientData clientData;

    private DataBaseServer server;

    public TripModel(DataBaseServer server) {
        this.server = server;
    }

    public void setPickedExcursion(LinkedList<ExcursionData> excursion) {
        assert null != excursion;
        pickedExcursion = excursion;
    }

    public void setBookingInfo(HotelBookingInfo info) {
        assert null != info;
        bookingInfo = info;
    }

    public void setTicketData(TicketData data) {
        assert null != data;
        ticketData = data;
    }

    public void commitTrip() {
        server.insertTrip(cargoDataLinkedList, ticketData,
                clientData, pickedExcursion, bookingInfo);
    }

    HotelBookingInfo getBookingInfo() {
        return bookingInfo;
    }

    LinkedList<ExcursionData> getPickedExcursion() {
        return pickedExcursion;
    }

    TicketData getTicketData() {
        return ticketData;
    }

    public void setClientData(ClientData client) {
        assert null != client;
        clientData = client;
    }

    ClientData getClientData() {
        return clientData;
    }

    public void setCargoDataLinkedList(LinkedList<CargoData> cargoDataLinkedList) {
        this.cargoDataLinkedList = cargoDataLinkedList;
    }

    public LinkedList<CargoData> getCargoDataLinkedList() {
        return cargoDataLinkedList;
    }
}
