package GUI.requests.infoabouttrip;

import Server.DataBaseServer;

import java.util.LinkedList;

public class InfoAboutController {
    private InfoAboutModel model;
    private InfoAboutView view;
    private int touristID;
    public InfoAboutController(DataBaseServer server, int tripID) {
        model = new InfoAboutModel(server);
        AboutTripDate date = model.getDate(tripID);
        String hostelName = model.getHostelName(tripID);
        LinkedList<AboutExcursion> dataExcursions = model.getExcursions(tripID);
        LinkedList<AboutCargo> dataCargo = model.getCargos(tripID);
        view = new InfoAboutView(this, date, hostelName, dataExcursions, dataCargo);
        view.setVisible(true);
    }

    InfoAboutModel getModel() {
        return model;
    }
}
