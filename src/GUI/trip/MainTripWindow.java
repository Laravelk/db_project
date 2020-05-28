package GUI.trip;

import Server.DataBaseServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class MainTripWindow extends JFrame {
    private boolean isFilter = false;
    private int filterID;
    private DataBaseServer server;

    public MainTripWindow(boolean isFilterByClient, int id, DataBaseServer server) {
        this.isFilter = isFilterByClient;
        if (isFilter) {
            this.filterID = id;
        }
        this.server = server;
        initWindow();
    }

    void initWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 1000);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        JTable tripsTable = initTripTable();
        JScrollPane pane = new JScrollPane(tripsTable);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridheight = 2;
        constraints.gridwidth = 4;
        add(pane, constraints);

        constraints = new GridBagConstraints();
        JButton edit_cargo_button = new JButton("Cargo");
        constraints.gridx = 5;
        constraints.gridy = 0;
        add(edit_cargo_button, constraints);

        JButton edit_excursion_button = new JButton("Excursion");
        constraints.gridx = 5;
        constraints.gridy = 1;
        add(edit_excursion_button, constraints);

        JButton edit_flight = new JButton("Flight");
        constraints.gridx = 5;
        constraints.gridy = 2;
        add(edit_flight, constraints);

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
            data = server.getTrips();
        } else {
            data = server.getClientsTrip(filterID);
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

        return table;
    }
}
