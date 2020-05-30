package GUI.requests.popularexcursion;

import Server.DataBaseServer;

import java.util.LinkedList;

public class PopularExcursionModel {
    private DataBaseServer server;
    PopularExcursionModel(DataBaseServer server) {
        this.server = server;
    }

    String getBestExcursion() {
        return server.getMostPopularExcursion();
    }

    LinkedList<String> getAgency() {
        return server.getGoodAgencyNames();
    }
}
