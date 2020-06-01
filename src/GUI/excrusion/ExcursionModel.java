package GUI.excrusion;

import Data.ExcursionData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class ExcursionModel {
    private LinkedList<ExcursionData> pickedExcursions = new LinkedList<>();
    private LinkedList<ExcursionData> allExcursions = new LinkedList<>();
    final private DataBaseServer server;

    ExcursionModel(DataBaseServer server, String dateIn, String dateOut) {
        this.server = server;
        allExcursions = server.getExcursion(dateIn, dateOut);
    }

    public void addExcursion(ExcursionData excursion) {
        pickedExcursions.add(excursion);
    }

    public void removeExcursion(ExcursionData excursion) {
        pickedExcursions.remove(excursion);
    }

    public LinkedList<ExcursionData> getExcursions() {
        return pickedExcursions;
    }

    public LinkedList<ExcursionData> getAllExcursions() {
        return allExcursions;
    }

    public void removeExcursionFromDataBase(int tripID, int excursionID) {
        server.removeExcursion(tripID, excursionID);
    }

    public void addExcursionToDataBase(int tripID, int excursionID, int clientID) {
        server.addExcursion(tripID, excursionID, clientID);
    }

    void clear() {
        pickedExcursions.clear();
    }
}
