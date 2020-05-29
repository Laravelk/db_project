package GUI.requests.infoabouttrip;

import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutModel {
    private DataBaseServer server;
    InfoAboutModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<AboutTripDate> getAllDates(int touristID) {
        return server.getAllDates(touristID);
    }

    public AboutTripDate getDate(int tripID) { return server.getDate(tripID); }

    public String getHostelName(int tripID) {return server.getHostelInTrip(tripID); }

    public LinkedList<AboutExcursion> getExcursions(int tripID ) { return server.getDataExcursionForRequest(tripID); }

    public LinkedList<AboutCargo> getCargos(int tripID) { return server.getAboutCargo(tripID); }
}
