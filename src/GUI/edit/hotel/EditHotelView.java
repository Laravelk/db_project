package GUI.edit.hotel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class EditHotelView extends JFrame {
    private EditHotelController controller;
    private final JComboBox<String> hotels = new JComboBox<String>();
    private JLabel price = new JLabel("");
    private JButton ok = new JButton("Ok");

    EditHotelView(EditHotelController controller) {
        this.controller = controller;
        setMinimumSize(new Dimension(270, 250));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        hotels.setSize(180, 70);
        LinkedList<String> hotels_list = controller.getModel().getHotels();
        System.out.println(hotels_list);
        for (String s : hotels_list) {
            hotels.addItem(s);
        }

        controller.getModel().setHotelName(Objects.requireNonNull(hotels.getSelectedItem()).toString());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        add(hotels, constraints);

        JLabel per = new JLabel("Per For Night: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(per, constraints);


        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(price, constraints);
        updatePrice();

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(ok, constraints);
    }

    public JComboBox<String> getHotels() {
        return hotels;
    }

    public JButton getOk() {
        return ok;
    }

    public JLabel getPrice() {
        return price;
    }

    void updatePrice() {
        int min_price = 100;
        int max_price = 4000;
        price.setText(String.valueOf(new Random().nextInt(max_price - min_price) + max_price));
    }
}
