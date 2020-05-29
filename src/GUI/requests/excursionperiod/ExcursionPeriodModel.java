package GUI.requests.excursionperiod;

import Server.DataBaseServer;

public class ExcursionPeriodModel {
    private DataBaseServer server;
    ExcursionPeriodModel(DataBaseServer server) {
        this.server = server;
    }

    public int getCountPerPeriod(String start, String end) {
        return server.getCountTouristInCountryPerPeriod(start, end);
    }
}
