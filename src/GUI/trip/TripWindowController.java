package GUI.trip;

import Server.DataBaseServer;

public class TripWindowController {
    DataBaseServer server;

    public TripWindowController(DataBaseServer server, boolean isFiltered, int id) {
        this.server = server;
        init(isFiltered, id);
    }

    private void init(boolean isFiltered, int id) {
            MainTripWindow mainTripWindow = new MainTripWindow(isFiltered, id, server);
            mainTripWindow.setVisible(true);
    }
}