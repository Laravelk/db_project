package GUI.mainexcursion;

import Data.ExcursionData;
import Server.DataBaseServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MainExcursionController {
    private MainExcursionModel model;
    private MainExcursionWindow view;
    public MainExcursionController(DataBaseServer server) {
        model = new MainExcursionModel(server);
        view = new MainExcursionWindow(this);

        view.getAddExcursion().addActionListener(actionEvent -> {
            ExcursionData data = new ExcursionData();

            AddExcursionDialog dialog = new AddExcursionDialog(model);
            dialog.getDate().addActionListener(actionEvent1 -> {
                data.setDate(dialog.getDate().getText());
                dialog.getRate().setEnabled(true);
            });

            dialog.getRate().addActionListener(actionEvent12 -> {
                data.setRate((Integer) dialog.getRate().getSelectedItem());
                dialog.getTitles().setEnabled(true);
            });

            dialog.getTitles().addActionListener(actionEvent13 -> {
                data.setTitle(dialog.getTitles().getText());
                dialog.getNameAgency().setEnabled(true);
            });

            dialog.getNameAgency().addActionListener(actionEvent14 -> {
                int agencyID = model.getIdAgency((String)dialog.getNameAgency().getSelectedItem());
                data.setAgencyID(agencyID);
                dialog.getPriceField().setEnabled(true);
            });

            dialog.getPriceField().addActionListener(actionEvent16 -> {
                data.setPrice(Integer.parseInt(dialog.getPriceField().getText()));
                dialog.getOk().setEnabled(true);
            });

            dialog.getOk().addActionListener(actionEvent15 -> {
                dialog.dispose();
                model.addExcursionData(data);
            });

            dialog.setVisible(true);
        });

        JTable excursionTable = view.getExcursionTable();
        view.getRemoveExcursion().addActionListener(actionEvent -> {
            for (int i : view.getExcursionTable().getSelectedRows()) {
                int id = Integer.parseInt(excursionTable.getValueAt(i, excursionTable.getColumnModel().
                        getColumnIndex("ID")).toString());
                if (checkChoiceExcursion(id)) {
                    ExcursionData excursionData = findExcursionByID(id,
                            model.getAllExcursionLOCAL());
                    model.removeExcursion(excursionData);
                }
            }
            excursionTable.updateUI();
            view.repaint();
            view.revalidate();
            view.updateTable();
        });

        view.setVisible(true);
    }

    public MainExcursionModel getModel() {
        return model;
    }

    boolean checkChoiceExcursion(final int index) {
        for (ExcursionData data : model.getAllExcursionLOCAL()) {
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
}
