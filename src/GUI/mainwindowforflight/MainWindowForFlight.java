package GUI.mainwindowforflight;

import Data.FlightData;
import GUI.requests.infoaboutcargo.DataAboutCargo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class MainWindowForFlight  extends JFrame {
    private MainControllerForFlight controller;
    private JTable flightTable;

    private boolean isTableSelection = false;
    private int selectionID = -1;
    private final JButton getDataAboutFlight = new JButton("Get Data");
    private final JButton removeButton = new JButton("Remove");
    private final JButton addButton = new JButton("Add");
    private final JButton addPlane = new JButton("Add plane");
    MainWindowForFlight(MainControllerForFlight controllerForFlight) {
        this.controller = controllerForFlight;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        setSize(600,450);

        GridBagConstraints constraints = new GridBagConstraints();
        JScrollPane pane = new JScrollPane(getFlightTable());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 4;
        add(pane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(removeButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 1;
        add(addButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 2;
        add(getDataAboutFlight, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 4;
        constraints.gridy = 3;
        add(addPlane, constraints);
    }

    private Vector<String> getHeadersForFlightTable() {
        Vector<String> headers = new Vector<>();
        headers.add("ID");
        headers.add("Дата");
        headers.add("Номер борта");
        headers.add("Номер транзакции");
        return headers;
    }

    private Vector<Vector<String>> getFlightData() {
        Vector<Vector<String>> data = new Vector<>();
        LinkedList<FlightData> flightList = controller.getModel().getAllFlight();

        for (FlightData flightData : flightList) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(flightData.getID()));
            row.add(flightData.getData());
            row.add(String.valueOf(flightData.getAirplaneData().getID()));
            row.add(String.valueOf(flightData.getIdTrans()));
            data.add(row);
        }
        return data;
    }

    private JTable getFlightTable() {
        Vector<String> headers = getHeadersForFlightTable();
        Vector<Vector<String>> data = getFlightData();
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        flightTable = new JTable(defaultTableModel);

        ListSelectionModel listSelectionModel = flightTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(listSelectionEvent -> {
            isTableSelection = true;
            int selectionRow = listSelectionEvent.getFirstIndex();
            selectionID = Integer.parseInt(defaultTableModel.getValueAt(selectionRow, 0).toString());
        });

        flightTable.setGridColor(Color.blue);
        flightTable.setShowVerticalLines(true);
        return flightTable;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel)flightTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        Vector<Vector<String>> data = getFlightData();
        for (int i = 0; i < data.size(); i++) {
            model.addRow(data.elementAt(i));
        }
    }

    public JButton getAddPlane() {
        return addPlane;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getAddButton() {
        return addButton;
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

    public void setSelectionID(int selectionID) {
        this.selectionID = selectionID;
    }

    public JButton getGetDataAboutFlight() {
        return getDataAboutFlight;
    }

    public JTable getFlightTables() {
        return flightTable;
    }
}
