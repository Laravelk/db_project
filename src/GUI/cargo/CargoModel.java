package GUI.cargo;

import Data.CargoData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class CargoModel {
    private DataBaseServer server;

    private LinkedList<CargoData> cargoDataLinkedList = new LinkedList<>();
    private CargoData data = new CargoData();

    CargoModel(DataBaseServer server) {
        this.server = server;
    }

    void insertPrepareData() {
        cargoDataLinkedList.add(data);
        data = new CargoData();
    }

    public LinkedList<CargoData> getCargoDataLinkedList() {
        return cargoDataLinkedList;
    }

    CargoData getPrepareData() {
        return data;
    }

    LinkedList<String> getWarehouse() {
        return server.getWarehouse();
    }
}
