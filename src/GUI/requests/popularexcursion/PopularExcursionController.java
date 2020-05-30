package GUI.requests.popularexcursion;

import Server.DataBaseServer;

public class PopularExcursionController {
    private PopularExcursionModel model;
    private PopularExcursionView view;
    public PopularExcursionController(DataBaseServer server) {
        model = new PopularExcursionModel(server);
        view = new PopularExcursionView(this);
        view.setVisible(true);
    }
    public PopularExcursionModel getModel() {
        return model;
    }
}
