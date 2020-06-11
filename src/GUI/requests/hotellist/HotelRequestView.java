package GUI.requests.hotellist;

import GUI.instruments.CategoryFilter;
import GUI.instruments.HotelFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class HotelRequestView extends JFrame {
    private HotelRequestController controller;
    private CategoryFilter categoryFilter;
    private HotelFilter hotelFilter;
    private JTable requestTable;

    private boolean isOnlyWork = false;
    private boolean isOnlyTravel = false;

    HotelRequestView(HotelRequestController controller) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(600, 450);

        setLayout(new GridBagLayout());

        categoryFilter = new CategoryFilter();
        hotelFilter = new HotelFilter(controller.getModel().getHotelNames(), controller);
        requestTable = createTable();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.gridheight = 5;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        JScrollPane scrollTable = new JScrollPane(requestTable);
        add(scrollTable, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        add(categoryFilter, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(hotelFilter, constraints);
    }

    private Vector<String> getHeader() {
        Vector<String> headers = new Vector<>();
        headers.add("NAME");
        headers.add("LAST NAME");
        headers.add("PASSPORT ID");
        headers.add("HOTEL NAME");
        headers.add("TRAVEL TARGET");
        return headers;
    }

    private Vector<Vector<String>> getData() {
        return  controller.getModel().getRequest(hotelFilter.getSelectedHotel(), isOnlyWork, isOnlyTravel);
    }

    private JTable createTable() {
        Vector<String> headers = getHeader();
        Vector<Vector<String>> data = getData();
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(defaultTableModel);
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        Vector<Vector<String>> data = getData();
        for (Vector<String> row : data) {
            model.addRow(row);
        }
        requestTable.updateUI();
        requestTable.repaint();
    }

    public CategoryFilter getCategoryFilter() {
        return categoryFilter;
    }

    public HotelFilter getHotelFilter() {
        return hotelFilter;
    }

    public JTable getRequestTable() {
        return requestTable;
    }

    public boolean isOnlyWork() {
        return isOnlyWork;
    }

    public boolean isOnlyTravel() {
        return isOnlyTravel;
    }

    public void setOnlyTravel(boolean onlyTravel) {
        isOnlyTravel = onlyTravel;
    }

    public void setOnlyWork(boolean onlyWork) {
        isOnlyWork = onlyWork;
    }
}
