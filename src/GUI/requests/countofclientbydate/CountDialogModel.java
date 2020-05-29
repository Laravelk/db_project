package GUI.requests.countofclientbydate;

import Server.DataBaseServer;

public class CountDialogModel {
    private DataBaseServer server;
    CountDialogModel(DataBaseServer server) {
        this.server = server;
    }

    int getCount(String dateIn, String dateOut, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.getCountOfPeople(dateIn, dateOut, isOnlyWork, isOnlyTravel);
    }
}
