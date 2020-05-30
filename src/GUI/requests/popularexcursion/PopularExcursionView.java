package GUI.requests.popularexcursion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class PopularExcursionView extends JFrame {
    private PopularExcursionController controller;
    PopularExcursionView(PopularExcursionController controller) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,500);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        JLabel bestExcursionName = new JLabel("Самая популярная экскурсия: " + controller.getModel().getBestExcursion());
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(bestExcursionName, constraints);

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

    private Vector<String> getHeadersForGoodAgency() {
        Vector<String> headers = new Vector<>();
        headers.add("Лучшие агенства");
        return headers;
    }

    private Vector<Vector<String>> getDataForGoodAgency() {
        Vector<Vector<String>> data = new Vector<>();
        LinkedList<String> linkedList = controller.getModel().getAgency();
        for (String agency : linkedList) {
            Vector<String> row = new Vector<>();
            row.add(agency);
            data.add(row);
        }
        return data;
    }

    private JTable getGoodExcursionTable() {
        Vector<String> headers = getHeadersForGoodAgency();
        Vector<Vector<String>> data = getDataForGoodAgency();
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(defaultTableModel);
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }
}
