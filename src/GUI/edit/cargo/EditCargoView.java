package GUI.edit.cargo;

import Data.CargoData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class EditCargoView extends JFrame {
    private EditCargoController controller;
    private boolean isTableSelection = false;
    private int selectionID = -1;
    private JTable tripsTable;
    private JButton removeButton = new JButton("Remove");
    EditCargoView(EditCargoController cargo) {
        this.controller = cargo;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        tripsTable = initTripTable();
        JScrollPane pane = new JScrollPane(tripsTable);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridheight = 6;
        constraints.gridwidth = 7;
        add(pane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 7;
        constraints.gridy = 1;
        add(removeButton, constraints);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel)tripsTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        isTableSelection = false;

        Vector<Vector<String>> data = getDataForTripTable();
        for (int i = 0; i < data.size(); i++) {
            model.addRow(data.elementAt(i));
        }
        tripsTable.updateUI();
        tripsTable.repaint();
    }

    private Vector<String> getHeadersForTripTable() {
        Vector<String> headers = new Vector<>();
        headers.add("ID");
        headers.add("State ID");
        headers.add("Kind");
        headers.add("Count");
        headers.add("Real Cost");
        headers.add("Insurance Cost");
        headers.add("Weight");
        headers.add("Volume");
        return headers;
    }

    private Vector<Vector<String>> getDataForTripTable() {
        Vector<Vector<String>> vector = new Vector<>();
        LinkedList<CargoData> cargoData = controller.getModel().getCargoDataByTripID(controller.getTripID());

        for (CargoData data : cargoData) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(data.getID()));
            row.add(String.valueOf(data.getStatementID()));
            row.add(String.valueOf(data.getKind()));
            row.add(String.valueOf(data.getCount()));
            row.add(String.valueOf(data.getReal_wrap()));
            row.add(String.valueOf(data.getCost_insurance()));
            row.add(String.valueOf(data.getWeight()));
            row.add(String.valueOf(data.getVolume()));
            vector.add(row);
        }
        return vector;
    }

    private JTable initTripTable() {
        Vector<String> headers = getHeadersForTripTable();
        Vector<Vector<String>> data = getDataForTripTable();

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 != column && 1 != column;
            }
        };
        JTable table = new JTable(defaultTableModel);

        ListSelectionModel listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(listSelectionEvent -> {
            isTableSelection = true;
            int selectionRow = listSelectionEvent.getFirstIndex(); // потому что он совпадает с выбранной строкой
            selectionID = Integer.parseInt(defaultTableModel.getValueAt(selectionRow, 0).toString());
        });
        return table;
    }

    public boolean isTableSelection() {
        return isTableSelection;
    }

    public void setTableSelection(boolean tableSelection) {
        isTableSelection = tableSelection;
    }

    public int getSelectionID() {
        return selectionID;
    }

    public JTable getTripsTable() {
        return tripsTable;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }
}
