package GUI.requests.infoaboutcargo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class InfoAboutCargoView extends JFrame {
    private InfoAboutCargoController cargoController;
    InfoAboutCargoView(InfoAboutCargoController cargoController) {
        this.cargoController = cargoController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(490,450);

        GridBagConstraints constraints = new GridBagConstraints();
        JScrollPane pane = new JScrollPane(getGoodExcursionTable());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridheight = 4;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.BOTH;
        add(pane, constraints);
    }

    private Vector<String> getHeadersForGoodAgency() {
        Vector<String> headers = new Vector<>();
        headers.add("Название груза");
        headers.add("Количество от общего потока");
        headers.add("Вес от общего потока");
        headers.add("Объем от общего потока");
        return headers;
    }

    private Vector<Vector<String>> getDataForGoodAgency() {
        Vector<Vector<String>> data = new Vector<>();
        LinkedList<DataAboutCargo> aboutCargos = cargoController.getModel().getInfoAboutCargo();

        for (DataAboutCargo aboutCargo : aboutCargos) {
            Vector<String> row = new Vector<>();
            row.add(aboutCargo.getKind());
            row.add(aboutCargo.getCount() * 100 + "%");
            row.add(aboutCargo.getWeight() * 100 + "%");
            row.add(aboutCargo.getVolume() * 100 + "%");
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
