package GUI.edit.cargo;

import Data.CargoData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class EditCargoModel {
    private DataBaseServer server;
    EditCargoModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<CargoData> getCargoDataByTripID(int tripID) {
        return server.getCargoFromTrip(tripID);
    }

    public void updateKindCargo(String newVal, int id) {
        server.updateKindInCargo(newVal, id);
    }

    public void updateStatement(String columnName, String newVal, int id) {
        server.updateStatement(columnName, newVal, id);
    }

    public void removeCargo(int cargoID, int statementID) {
        server.removeCargo(cargoID, statementID);
    }
}
