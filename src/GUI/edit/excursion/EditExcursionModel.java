package GUI.edit.excursion;

import Data.ExcursionData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class EditExcursionModel {
    private DataBaseServer server;
    public EditExcursionModel(DataBaseServer server) {
        this.server = server;
    }

    LinkedList<ExcursionData> getExcursions(int tripID) {
        return server.getExcursionFromTrip(tripID);
    }
}
