package GUI.edit.cargo;

import Server.DataBaseServer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCargoController {
    private EditCargoModel model;
    private EditCargoView view;
    private int tripID;
    public EditCargoController(DataBaseServer server, int tripID) {
        this.tripID = tripID;
        model = new EditCargoModel(server);
        view = new EditCargoView(this);

        view.getRemoveButton().addActionListener(actionEvent -> {
            if (view.isTableSelection()) {
                int cargoID = Integer.parseInt(String.valueOf(view.getTripsTable().
                        getModel().getValueAt(view.getTripsTable().getSelectedRow(), 0)));
                int idStatement = Integer.parseInt(String.valueOf(view.getTripsTable().
                        getModel().getValueAt(view.getTripsTable().getSelectedRow(), 1)));
                model.removeCargo(cargoID, idStatement);
                view.setTableSelection(false);
                view.updateTable();
            }
        });

        view.getTripsTable().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        view.getTripsTable().getModel().addTableModelListener(tableModelEvent -> {
            int id = Integer.parseInt(String.valueOf(view.getTripsTable().
                    getModel().getValueAt(view.getTripsTable().getEditingRow(), 0)));
            int idStatement = Integer.parseInt(String.valueOf(view.getTripsTable().
                    getModel().getValueAt(view.getTripsTable().getEditingRow(), 1)));
            System.out.println(id + " " + idStatement);
            updateSomething(id, idStatement, view.getTripsTable().getEditingRow(),
                    view.getTripsTable().getEditingColumn());
        });

        view.setVisible(true);
    }

    private void updateSomething(int id, int idStatement, int row, int column) {
        String changeString = String.valueOf(view.getTripsTable().getModel().getValueAt(row, column));
        String columnName = "";
        if (2 == column) {
            columnName = "KIND";
            model.updateKindCargo(changeString, id);
            return;
        }

        if (3 == column) {
            columnName = "COUNT";
        }
        if (4 == column) {
            columnName = "COST_WRAP";
        }
        if (5 == column) {
            columnName = "COST_INSURANCE";
        }
        if (6 == column) {
            columnName = "WEIGHT";
        }
        if (7 == column) {
            columnName = "VOLUME";
        }
        model.updateStatement(columnName, changeString, idStatement);
    }

    public EditCargoModel getModel() {
        return model;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
