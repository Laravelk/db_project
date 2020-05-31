package GUI.requests.infoaboutcargo;

import Server.DataBaseServer;

public class InfoAboutCargoController {
    private InfoAboutCargoModel model;
    private InfoAboutCargoView view;
    public InfoAboutCargoController(DataBaseServer server) {
        model = new InfoAboutCargoModel(server);
        view = new InfoAboutCargoView(this);

        view.setVisible(true);
    }

    public InfoAboutCargoModel getModel() {
        return model;
    }
}
