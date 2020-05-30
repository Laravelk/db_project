package GUI.requests.infoaboutwarehouse;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;

public class InfoAboutWarehouseView extends JFrame {
    private InfoAboutWarehouseController controller;
    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;

    private JLabel countPlace = new JLabel("Количество мест: " );
    private JLabel weight = new JLabel("Вес груза: " );
    private JLabel countOfPlane = new JLabel("Количество самолетов: " );
    private JLabel countOfCargoPlane = new JLabel("Количество грузовых самолетов: ");
    private JLabel countOfPassengerPlane = new JLabel("Количество пассажирских самолетов");


    JComboBox<String> warehouses;
    public InfoAboutWarehouseView(InfoAboutWarehouseController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setSize(260, 220);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GridBagConstraints constraints = new GridBagConstraints();
        dateIn = new JFormattedTextField(maskFormatter);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        dateOut = new JFormattedTextField(maskFormatter);
        dateOut.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        add(dateOut, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel warehouseIDlabel = new JLabel("Warehouse ID: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(warehouseIDlabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        warehouses = new JComboBox<>();
        LinkedList<String> listOfWarehouses = controller.getModel().getWarehouse();
        warehouses.setSize(100, 40);
        for (String id : listOfWarehouses) {
            warehouses.addItem(id);
        }
        warehouses.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 3;
        add(warehouses, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 4;
        add(countPlace, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 5;
        add(weight, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 6;
        add(countOfPlane, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 7;
        add(countOfCargoPlane, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 8;
        add(countOfPassengerPlane, constraints);
    }

    public JComboBox<String> getWarehouses() {
        return warehouses;
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public void updateLabels(DataAboutWarehouse dataAboutWarehouse,
                        DataAboutFlightsWithWarehouse dataAboutFlightsWithWarehouse) {
        countOfPlane.setText("Количество мест: " + dataAboutWarehouse.getCount());
        weight.setText("Вес груза: " + dataAboutWarehouse.getWeightSum());
        countOfPlane.setText("Количество самолетов: " + dataAboutFlightsWithWarehouse.getAllCount());
        countOfCargoPlane.setText("Количество грузовых самолетов: " + dataAboutFlightsWithWarehouse.getCargoCount());
        countOfPassengerPlane.setText("Количество пассажирских самолетов: " +
                dataAboutFlightsWithWarehouse.getPassengerCount());
    }
}
