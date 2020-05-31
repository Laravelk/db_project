package GUI.requests.dataaboutflight;

import GUI.requests.infoabouttrip.AboutCargo;
import GUI.requests.infoabouttrip.AboutExcursion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class AboutPassengersDialog extends JDialog {
    private AboutFlightController controller;
    AboutPassengersDialog(AboutFlightController controller, LinkedList<AboutPassengerData> aboutPassengerData) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,450);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        JTable table = creatTable(aboutPassengerData);
        JScrollPane pane = new JScrollPane(table);
        add(pane, constraints);
        setVisible(true);
    }

    private Vector<String> getPassengerHeader() {
        Vector<String> headers = new Vector<>();
        headers.add("Name");
        headers.add("Last Name");
        headers.add("ID GROUP");
        headers.add("Mark cargo");
        headers.add("Kind cargo");
        headers.add("Birk cargo");
        headers.add("Hotel name");
        return headers;
    }

    private Vector<Vector<String>> getPassengerData(LinkedList<AboutPassengerData> passengerData) {
        Vector<Vector<String>> vector = new Vector<>();
        for (AboutPassengerData data : passengerData) {
            Vector<String> row = new Vector<>();
            row.add(data.getName());
            row.add(data.getLastName());
            row.add(String.valueOf(data.getGroupNumber()));
            row.add(String.valueOf(data.getMark()));
            row.add(data.getKind());
            row.add(String.valueOf(data.getBirk()));
            row.add(data.getHotelName());
            vector.add(row);
        }
        return vector;
    }

    private JTable creatTable(LinkedList<AboutPassengerData> passengerData) {
        Vector<String> headers = getPassengerHeader();
        Vector<Vector<String>> data = getPassengerData(passengerData);
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(defaultTableModel);
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }
}
