package GUI.requests.dataaboutflight;

import Data.FlightData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Vector;

public class AboutFlightView extends JFrame {
    private AboutFlightController controller;
    private JTable flightTable;
    private JFormattedTextField dateIn;
    private JButton getInfo;
    private boolean isSelected;
    private int selectedID = -1;
    AboutFlightView(AboutFlightController controller) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,500);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateIn = new JFormattedTextField(maskFormatter);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(dateIn, constraints);

        getInfo = new JButton("Get Info");
        getGetInfo().setEnabled(false);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        add(getInfo, constraints);

        constraints = new GridBagConstraints();
        JScrollPane pane = new JScrollPane(getGoodExcursionTable());
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridheight = 4;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.BOTH;
        add(pane, constraints);
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JTable getFlightTable() {
        return flightTable;
    }

    private Vector<String> getHeaders() {
        Vector<String> headers = new Vector<>();
        headers.add("ID");
        headers.add("ID PLANE");
        headers.add("DATE");
        return headers;
    }

    public void updateTable(String date) {
        DefaultTableModel model = (DefaultTableModel)flightTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        isSelected = false;

        Vector<Vector<String>> data = getData(date);
        for (int i = 0; i < data.size(); i++) {
            model.addRow(data.elementAt(i));
        }
        flightTable.updateUI();
        flightTable.repaint();
    }

    private Vector<Vector<String>> getData(String date) {
        Vector<Vector<String>> data = new Vector<>();
        LinkedList<FlightData> linkedList = controller.getModel().getFlights(date);
        for (FlightData flightData : linkedList) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(flightData.getID()));
            row.add(String.valueOf(flightData.getAirplaneData().getID()));
            row.add(flightData.getData());
            data.add(row);
        }
        return data;
    }

    private JTable getGoodExcursionTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(getHeaders());
        flightTable = new JTable(defaultTableModel);
        flightTable.setGridColor(Color.blue);
        flightTable.setShowVerticalLines(true);
        return flightTable;
    }

    public JButton getGetInfo() {
        return getInfo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getSelectedID() {
        return selectedID;
    }

    public void setSelectedID(int selectedID) {
        this.selectedID = selectedID;
    }
}
