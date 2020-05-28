package GUI.mainwindow;

import Data.ClientData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class MainModel {
    private DataBaseServer server;

    MainModel(DataBaseServer server) {
        this.server = server;
    }

    LinkedList<ClientData> getClientsData() {
        return server.getClientsData();
    }

    LinkedList<ClientData> getClientDataByCategory(String category) {
       return server.getClientsDataByCategory(category);
    }
}
