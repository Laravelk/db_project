package GUI.mainwindowforflight;

import Data.AirplaneData;
import Data.FlightData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class MainModelForFlight {
    private DataBaseServer server;
    MainModelForFlight(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<FlightData> getAllFlight() {
        return server.getAllFlightData();
    }

    public void removeByID(int flightID) {
        server.removeFlight(flightID);
    }

    public LinkedList<AirplaneData> getAllAirplanes() {
        return server.getAllAirplane();
    }

    public void addAirplane(AirplaneData data) {
        server.insertAirplane(data);
    }

    public void commit(FlightData data) {
        server.insertAFlight(data);
    }
}
