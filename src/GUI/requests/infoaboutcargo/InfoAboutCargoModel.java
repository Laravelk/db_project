package GUI.requests.infoaboutcargo;

import GUI.requests.infoabouttrip.AboutCargo;
import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutCargoModel {
    private DataBaseServer server;
    InfoAboutCargoModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<DataAboutCargo> getInfoAboutCargo() {
        return server.getDataAboutCargosStream();
    }
}
