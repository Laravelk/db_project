package GUI.requests.shopclients;

import Server.DataBaseServer;

public class ShopClientsModel {
    private DataBaseServer server;
    ShopClientsModel(DataBaseServer server) {
        this.server = server;
    }

    public int count(String dateIn, String dateOut) {
        return server.getPercentOfShopTourToAnother(dateIn, dateOut);
    }
}
