package GUI.requests.dataaboutflight;

import Data.FlightData;
import Data.TicketData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class AboutFlightModel {
    private DataBaseServer server;
    LinkedList<FlightData> flightData;
    AboutFlightModel(DataBaseServer server) {
        this.server = server;
    }

    public FlightData getFlightDataByID(int ID) {
        for (FlightData data : flightData) {
            if (ID == data.getID()) {
                return data;
            }
        }
        return null;
    }

    AboutFlightData getDataAboutFlight(int flightID, boolean isCargoPlane) {
        return server.getDataAboutFlight(flightID, isCargoPlane);
    }

    LinkedList<FlightData> getFlights(String date) {
        flightData = server.getFlightByData(date);
        return flightData;
    }

    LinkedList<AboutPassengerData> getPassengerDara(int flightID) {
        return server.getAboutPassengerData(flightID);
    }

}
