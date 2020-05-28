package GUI.excrusion;

import Data.ExcursionData;
import GUI.Controller.TripController;
import Server.DataBaseServer;

import javax.swing.*;
import java.util.LinkedList;

public class ExcursionController {
    final private ExcursionModel model;
    private ExcursionWindow view;

    final private TripController tripWindowController;

    public ExcursionController(DataBaseServer server,
                               TripController tripWindowController) {
        this.tripWindowController = tripWindowController;
        model = new ExcursionModel(server);
        view = new ExcursionWindow(model.getAllExcursions(), this);

        JTable excursionTable = view.getExcursionTable();
        view.getAddExcursion().addActionListener(actionEvent -> {
            System.out.println(excursionTable);
            for (int i : excursionTable.getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt
                        (i, excursionTable.getColumnModel().getColumnIndex("ID"))
                        .toString());
                if (!checkChoiceExcursion(id)) {
                    System.out.println("ADD");
                    ExcursionData excursionData = findExcursionByID
                            (id, model.getAllExcursions());
                    model.addExcursion(excursionData);
                }
            }
            excursionTable.updateUI();
            view.repaint();
            view.revalidate();
        });

        view.getRemoveExcursion().addActionListener(actionEvent -> {
            System.out.println(excursionTable);
            for (int i : excursionTable.getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt(i, excursionTable.getColumnModel().getColumnIndex("ID")).toString());
                if (checkChoiceExcursion(id)) {
                    ExcursionData excursionData = findExcursionByID(id,
                            model.getAllExcursions());
                    model.removeExcursion(excursionData);
                }
            }
            excursionTable.updateUI();
            view.repaint();
            view.revalidate();
        });

        view.getFinishButton().addActionListener(actionEvent -> {
            tripWindowController.commitWindow
                    (tripWindowController.getWindowNumber());
            view.dispose();
        });

        view.getPrevButton().addActionListener(actionEvent -> {
            tripWindowController.changeWindow
                    (tripWindowController.getWindowNumber() - 1);
            view.dispose();
        });

        view.setVisible(false);
    }

    boolean checkChoiceExcursion(final int index) {
        //System.out.println(model.getExcursions().size());
        for (ExcursionData data : model.getExcursions()) {
            if (index == data.getExcursionID()) {
                return true;
            }
        }
        return false;
    }

    private ExcursionData findExcursionByID(final int ID, LinkedList<ExcursionData> list) {
        for (ExcursionData excursionData : list) {
            if (ID == excursionData.getExcursionID()) {
                return excursionData;
            }
        }
        return null;
    }

    public void clear() {
        model.clear();
        view = new ExcursionWindow(model.getAllExcursions(), this);
    }

    public ExcursionModel getModel() {
        return model;
    }

    public void setVisible(boolean t) {
        view.setVisible(t);
    }

}
