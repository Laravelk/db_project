package GUI.mainexcursion;

import Data.ExcursionData;
import Server.DataBaseServer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class MainExcursionWindow extends JFrame {
    private MainExcursionController controller;
    private final JButton addExcursion = new JButton("Add excursion");
    private final JButton removeExcursion = new JButton("Remove excursion");
    private JTable excursionTable;

    public MainExcursionWindow(MainExcursionController controller) {
        this.controller = controller;
        setMinimumSize(new Dimension(1400, 700));
        GridBagConstraints constraints = new GridBagConstraints();

        setLayout(new GridBagLayout());

        LinkedList<ExcursionData> excursionData = controller.getModel().getAllExcursionData();

        excursionTable = createExcursionTable(excursionData);
        JScrollPane scrollPane = new JScrollPane(excursionTable);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        add(scrollPane, constraints);

        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(addExcursion, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.fill = constraints.NORTH;
        constraints.gridy = 1;
        add(removeExcursion, constraints);
    }

    private JTable createExcursionTable(LinkedList<ExcursionData> list) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(getExcursionTableData(list), getExcursionTableHeader()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(defaultTableModel);
        table.setRowHeight(30);
        table.setRowHeight(1, 20);
        table.setIntercellSpacing(new Dimension(10,10));
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }

    private Vector<String> getExcursionTableHeader() {
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("TITLE");
        header.add("DATE");
        header.add("PRICE");
        header.add("RATE");
        return header;
    }

    private Vector<Vector<String>> getExcursionTableData(LinkedList<ExcursionData> list) {
        Vector<Vector<String>> data = new Vector<Vector<String>>();

        for (ExcursionData excursionData : list) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(excursionData.getExcursionID()));
            row.add(excursionData.getTitle());
            row.add(excursionData.getDate());
            row.add(String.valueOf(excursionData.getPrice()));
            row.add(String.valueOf(excursionData.getRate()));
            data.add(row);
        }
        return data;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel)excursionTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        Vector<Vector<String>> data = getExcursionTableData(controller.getModel().getAllExcursionData());
        for (int i = 0; i < data.size(); i++) {
            model.addRow(data.elementAt(i));
        }
    }

    public JButton getAddExcursion() {
        return addExcursion;
    }

    public JButton getRemoveExcursion() {
        return removeExcursion;
    }

    public JTable getExcursionTable() {
        return excursionTable;
    }
}
