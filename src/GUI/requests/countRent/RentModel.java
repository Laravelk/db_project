package GUI.requests.countRent;

import GUI.requests.paymentinfo.PaymentData;
import Server.DataBaseServer;

public class RentModel {
    private DataBaseServer server;
    RentModel(DataBaseServer server) {
        this.server = server;
    }

    public int countRent() {
        return server.getRent();
    }
}
