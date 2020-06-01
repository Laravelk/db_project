package GUI.excrusion;

import Data.ExcursionData;
import GUI.Controller.TripController;
import GUI.edit.excursion.EditExcursionController;
import Server.DataBaseServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ExcursionController {
    final private ExcursionModel model;
    private ExcursionWindow view;

    private TripController tripWindowController;

    public ExcursionController(DataBaseServer server,
                               TripController tripWindowController, String dateIn, String dateOut) {
        this.tripWindowController = tripWindowController;
        model = new ExcursionModel(server, dateIn, dateOut);
        view = new ExcursionWindow(model.getAllExcursions(), this, true);

        JTable excursionTable = view.getExcursionTable();
        view.getAddExcursion().addActionListener(actionEvent -> {
            System.out.println(excursionTable);
            for (int i : excursionTable.getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt
                        (i, excursionTable.getColumnModel().getColumnIndex("ID"))
                        .toString());
                if (!checkChoiceExcursion(id)) {
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
                int id = Integer.parseInt(excursionTable.getValueAt(i, excursionTable.getColumnModel().
                        getColumnIndex("ID")).toString());
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

    private int clientID;
    private int tripID;

    public ExcursionController(DataBaseServer server, EditExcursionController controller,
                               LinkedList<ExcursionData> excursionData, String dateIn, String dateOut, int clientID, int tripID) {
        this.clientID = clientID;
        this.tripID = tripID;
        model = new ExcursionModel(server, dateIn, dateOut);
        view = new ExcursionWindow(model.getAllExcursions(), this, false);

        JTable excursionTable = view.getExcursionTable();

        for (ExcursionData data : excursionData) {
            model.addExcursion(data);
        }

        view.getExcursionTable().updateUI();
        view.revalidate();
        view.revalidate();

        view.getAddExcursion().addActionListener(actionEvent -> {
            for (int i : excursionTable.getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt
                        (i, excursionTable.getColumnModel().getColumnIndex("ID"))
                        .toString());
                if (!checkChoiceExcursion(id)) {
                    ExcursionData excursionDat = findExcursionByID
                            (id, model.getAllExcursions());
                    model.addExcursion(excursionDat);
                }
            }
            excursionTable.updateUI();
            view.repaint();
            view.revalidate();
        });

        view.getRemoveExcursion().addActionListener(actionEvent -> {
            for (int i : excursionTable.getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt(i, excursionTable.getColumnModel().
                        getColumnIndex("ID")).toString());
                if (checkChoiceExcursion(id)) {
                    ExcursionData excursionDat = findExcursionByID(id,
                            model.getAllExcursions());
                    model.removeExcursion(excursionDat);
                }
            }
            excursionTable.updateUI();
            view.repaint();
            view.revalidate();
        });

        view.getOk().addActionListener(actionEvent -> {
            LinkedList<ExcursionData> add = new LinkedList<>();
            for (ExcursionData data : model.getExcursions()) {
                if (!excursionData.contains(data)) {
                    add.add(data);
                } else {
                    excursionData.remove(data);
                }
            }
            updateDataBase(excursionData, add);
            view.dispose();
        });
        view.setVisible(true);
    }

    private void updateDataBase(LinkedList<ExcursionData> delete, LinkedList<ExcursionData> add) {
            for (ExcursionData del : delete) {
                model.removeExcursionFromDataBase(tripID, del.getExcursionID());
            }

            for (ExcursionData ad : add) {
                model.addExcursionToDataBase(tripID, ad.getExcursionID(), clientID);
            }
    }

    boolean checkChoiceExcursion(final int index) {
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
        view = new ExcursionWindow(model.getAllExcursions(), this, true);
    }

    public ExcursionModel getModel() {
        return model;
    }

    public void setVisible(boolean t) {
        view.setVisible(t);
    }

}
