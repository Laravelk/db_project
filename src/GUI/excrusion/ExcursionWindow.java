package GUI.excrusion;

import Data.ExcursionData;
import GUI.excrusion.ExcursionController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class ExcursionWindow extends JFrame {
    private final JButton addExcursion = new JButton("Add excursion");
    private final JButton removeExcursion = new JButton("Remove excursion");
    private final JButton finishButton = new JButton("finish");
    private final JButton prevButton = new JButton("prev");
    private JTable excursionTable;

    private final ExcursionController controller;

    JButton getAddExcursion() {
        return addExcursion;
    }

    JButton getRemoveExcursion() {
        return removeExcursion;
    }

    JButton getPrevButton() {
        return prevButton;
    }

    JButton getFinishButton() {
        return finishButton;
    }

    JTable getExcursionTable() {
        return excursionTable;
    }

    ExcursionWindow(LinkedList<ExcursionData> excursionData, ExcursionController controller) {
        this.controller = controller;
        setMinimumSize(new Dimension(1400, 800));
        GridBagConstraints constraints = new GridBagConstraints();

        setLayout(new GridBagLayout());

        LinkedList<ExcursionData> list = excursionData;
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
        constraints.gridy = 1;
        add(removeExcursion, constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        add(finishButton, constraints);

        constraints.gridx = 4;
        constraints.gridy = 3;
        add(prevButton, constraints);
    }

    private JTable createExcursionTable(LinkedList<ExcursionData> list) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(getExcursionTableData(list), getExcursionTableHeader()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(defaultTableModel);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String id = table.getValueAt(row, table.getColumnModel().getColumnIndex("ID")).toString();
                if (controller.checkChoiceExcursion(Integer.parseInt(id))) {
                    cell.setForeground(Color.green);
                } else {
                    cell.setForeground(Color.black);
                }
                return cell;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

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
}
