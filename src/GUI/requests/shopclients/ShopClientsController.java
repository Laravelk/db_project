package GUI.requests.shopclients;

import Server.DataBaseServer;

public class ShopClientsController {
    private ShopClientsModel model;
    private ShopClientsView view;
    public ShopClientsController(DataBaseServer server) {
        model = new ShopClientsModel(server);
        view = new ShopClientsView(this);

        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
               update();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getDateOut().addActionListener(actionEvent -> {
            update();
        });

        view.setVisible(true);
    }

    private void update() {
        int percent = model.count(view.getDateIn().getText(), view.getDateOut().getText());
        view.updateInfo(percent);
    }

    public ShopClientsModel getModel() {
        return model;
    }
}
