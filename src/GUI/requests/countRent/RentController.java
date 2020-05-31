package GUI.requests.countRent;

import Server.DataBaseServer;

public class RentController {
    RentView view;
    RentModel model;
    public RentController(DataBaseServer server) {
        model = new RentModel(server);
        view = new RentView(this);

        view.setVisible(true);
    }

    public RentModel getModel() {
        return model;
    }
}
