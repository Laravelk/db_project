package GUI.mainexcursion;

import Data.ExcursionData;
import Server.DataBaseServer;

import java.util.LinkedList;

public class MainExcursionModel {
    private DataBaseServer server;
    LinkedList<ExcursionData> allExcursion;
    MainExcursionModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<ExcursionData> getAllExcursionLOCAL() {
        return allExcursion;
    }

    public LinkedList<ExcursionData> getAllExcursionData() {
        allExcursion = server.getAllExcursion();
        return allExcursion;
    }

    public void removeExcursion(ExcursionData data) {
        server.removeExcursion(data);
    }

    public LinkedList<String> getAgencyNames() {
        return server.getAllAgency();
    }

    public int getIdAgency(String nameAgency) {
        return server.getIdAgency(nameAgency);
    }

    public void addExcursionData(ExcursionData data) {
        server.addExcursionData(data);
    }
}
