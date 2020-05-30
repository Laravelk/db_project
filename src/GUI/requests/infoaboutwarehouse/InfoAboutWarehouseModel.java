package GUI.requests.infoaboutwarehouse;

import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutWarehouseModel {
    private DataBaseServer server;

    InfoAboutWarehouseModel(DataBaseServer server) {
        this.server = server;
    }

    LinkedList<String> getWarehouse() {
        return server.getWarehouse();
    }

    DataAboutWarehouse getWarehouseData(int warehouseID, String dateIn, String dateOut) {
        return server.getWarehouseData(warehouseID, dateIn, dateOut);
    }

    DataAboutFlightsWithWarehouse getFlights(int warehouseID, String dateIn, String dateOut) {
        return server.getWarehouseDataAroundFlight(warehouseID, dateIn, dateOut);
    }
}
