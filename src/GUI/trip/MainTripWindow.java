package GUI.trip;

import Server.DataBaseServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class MainTripWindow extends JFrame {
    private boolean isFilter = false;
    private int filterID;

    private TripWindowController controller;

    private JButton editFlight = new JButton("Edit Flight");
    private JButton editCargo = new JButton("Edit Cargo");
    private JButton editExcursion = new JButton("Edit Excursion");
    private JButton editBooking = new JButton("Edit Booking");
    private JButton getInfoAboutTrip = new JButton("Get Info About Trip");

    private boolean isTableSelection = false;
    private int selectionID = -1;

    public MainTripWindow(boolean isFilterByClient, int id, TripWindowController controller) {
        this.isFilter = isFilterByClient;
        if (isFilter) {
            this.filterID = id;
        }
        this.controller = controller;
        initWindow();
    }

    void initWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        JTable tripsTable = initTripTable();
        JScrollPane pane = new JScrollPane(tripsTable);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridheight = 6;
        constraints.gridwidth = 7;
        add(pane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 7;
        constraints.gridy = 0;
        add(editCargo, constraints);

        constraints.gridx = 7;
        constraints.gridy = 1;
        add(editExcursion, constraints);

        constraints.gridx = 7;
        constraints.gridy = 2;
        add(editFlight, constraints);

        constraints.gridx = 7;
        constraints.gridy = 3;
        add(editExcursion, constraints);

        constraints.gridx = 7;
        constraints.gridy = 4;
        add(editBooking, constraints);

    }


    private Vector<String> getHeadersForTripTable() {
        Vector<String> headers = new Vector<>();

        headers.add("Trip ID");
        headers.add("Date in");
        headers.add("Date out");

        return headers;
    }

    private Vector<Vector<String>> getDataForTripTable() {
        Vector<Vector<String>> data = new Vector<>();
        if (!isFilter) {
            data = controller.getModel().getTrips();
        } else {
            data = controller.getModel().getClientsTrip(filterID);
        }
        return data;
    }

    private JTable initTripTable() {
        Vector<String> headers = getHeadersForTripTable();
        Vector<Vector<String>> data = getDataForTripTable();

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(defaultTableModel);

        ListSelectionModel listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(listSelectionEvent -> {
            isTableSelection = true;
            int selectionRow = listSelectionEvent.getFirstIndex(); // потому что он совпадает с выбранной строкой
            selectionID = Integer.parseInt(defaultTableModel.getValueAt(selectionRow, 0).toString());
            controller.setSelectionId(selectionID);
        });
        return table;
    }

    public JButton getEditBooking() {
        return editBooking;
    }

    public JButton getEditExcursion() {
        return editExcursion;
    }

    public JButton getEditCargo() {
        return editCargo;
    }

    public JButton getEditFlight() {
        return editFlight;
    }

    public int getSelectionID() {
        return selectionID;
    }

    public boolean isTableSelection() {
        return isTableSelection;
    }
}
