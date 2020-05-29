package GUI.requests.infoabouthotel;

import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutHotelController {
    private InfoAboutHotelModel model;
    private InfoAboutHotelView view;

    public InfoAboutHotelController(DataBaseServer server) {
        model =  new InfoAboutHotelModel(server);
        view = new InfoAboutHotelView(this);

        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
                updateInfo();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getDateOut().addActionListener(actionEvent -> {
            updateInfo();
        });

        view.setVisible(true);
    }

    private void updateInfo() {
        LinkedList<Integer> count = model.getCount(view.getDateIn().getText(),
                                                    view.getDateOut().getText());
        view.updateInfo(count);
    }

    public InfoAboutHotelModel getModel() {
        return model;
    }
}
