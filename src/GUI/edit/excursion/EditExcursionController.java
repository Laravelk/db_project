package GUI.edit.excursion;

import Data.ExcursionData;
import GUI.excrusion.ExcursionController;
import GUI.excrusion.ExcursionWindow;
import Server.DataBaseServer;

import java.util.LinkedList;

public class EditExcursionController {
    private EditExcursionModel model;
    private ExcursionController view;
    private int tripID;
    private int clientID;
    public EditExcursionController(DataBaseServer server, int tripID, String dateIn, String dateOut, int clientID) {
        model = new EditExcursionModel(server);
        LinkedList<ExcursionData> excursionData = model.getExcursions(tripID);
        view = new ExcursionController(server, this, excursionData, dateIn, dateOut, clientID, tripID);
    }
}
