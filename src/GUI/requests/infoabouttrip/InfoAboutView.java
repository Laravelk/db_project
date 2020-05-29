package GUI.requests.infoabouttrip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class InfoAboutView extends JFrame {

    InfoAboutView(InfoAboutController controller, AboutTripDate tripDate, String hotelNameString,
                  LinkedList<AboutExcursion> excursions, LinkedList<AboutCargo> cargos) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(1100,450);

        GridBagConstraints constraints = new GridBagConstraints();

        JScrollPane pane1 = new JScrollPane(createCargosTable(cargos));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 3;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        add(pane1, constraints);

        constraints = new GridBagConstraints();
        JScrollPane pane2 = new JScrollPane(createExcursionTable(excursions));
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 3;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        add(pane2, constraints);

        constraints = new GridBagConstraints();
        JLabel hotelName = new JLabel("Hotel: " + hotelNameString);
        constraints.gridx = 8;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(hotelName, constraints);

        constraints = new GridBagConstraints();
        JLabel dateIn = new JLabel("Date in: " + tripDate.getDateIn());
        constraints.gridx = 8;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        JLabel dateOut = new JLabel("Date Out: " + tripDate.getDateOut());
        constraints.gridx = 8;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        add(dateOut, constraints);

    }

    private Vector<String> getCargoHeaders() {
        Vector<String> headers = new Vector<>();
        headers.add("Kind");
        headers.add("Weight");
        headers.add("Count");
        return headers;
    }

    private Vector<String> getExcursionHeaders() {
        Vector<String> headers = new Vector<>();
        headers.add("Excursion Name");
        headers.add("Agency Name");
        return headers;
    }

    private Vector<Vector<String>> getExcursionData(LinkedList<AboutExcursion> excursions) {
        Vector<Vector<String>> vector = new Vector<>();
        for (AboutExcursion excursion : excursions) {
            Vector<String> row = new Vector<>();
            row.add(excursion.getNameExcursion());
            row.add(excursion.getNameAgency());
            vector.add(row);
        }
        return vector;
    }

    private Vector<Vector<String>> getCargoData(LinkedList<AboutCargo> cargos) {
        Vector<Vector<String>> vector = new Vector<>();
        for (AboutCargo cargo : cargos) {
            Vector<String> row = new Vector<>();
            row.add(cargo.getName());
            row.add(String.valueOf(cargo.getWeight()));
            row.add(String.valueOf(cargo.getCount()));
            vector.add(row);
        }
        return vector;
    }

    private JTable createExcursionTable(LinkedList<AboutExcursion> excursions) {
        Vector<String> headers = getExcursionHeaders();
        Vector<Vector<String>> data = getExcursionData(excursions);
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(defaultTableModel);
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }

    private JTable createCargosTable(LinkedList<AboutCargo> cargos) {
        Vector<String> headers = getCargoHeaders();
        Vector<Vector<String>> data = getCargoData(cargos);
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
        JTable table = new JTable(defaultTableModel);
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(true);
        return table;
    }
}
