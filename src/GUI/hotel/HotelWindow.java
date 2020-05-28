package GUI.hotel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Random;

public class HotelWindow extends JFrame {
    private final HotelController controller;
    private final JButton prevButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JFormattedTextField dateOut;
    private final JFormattedTextField dateIn;
    private final JComboBox<String> hotels = new JComboBox<String>();

    private final JLabel priceInInt = new JLabel("");

    HotelWindow(HotelController controller) throws ParseException {
        this.controller = controller;
        setMinimumSize(new Dimension(270, 170));

        setLayout(new GridBagLayout());

        prevButton.setEnabled(true);
        nextButton.setEnabled(false);

        int min_price = 100;
        int max_price = 4000;

        hotels.setSize(180, 40);
        LinkedList<String> hotels_list = controller.getModel().getHotels();
        System.out.println(hotels_list);
        for (String s : hotels_list) {
            hotels.addItem(s);
        }

        controller.getModel().setName(hotels_list.getFirst());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        add(hotels, constraints);

        constraints = new GridBagConstraints();
        JLabel price = new JLabel("Price Per Night: ");
        price.setFont(new Font("TimesRoman", Font.BOLD, 16));
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(price, constraints);

        constraints = new GridBagConstraints();
        constraints.gridy = 1;
        constraints.gridx = 1;
        add(priceInInt, constraints);
        priceInInt.setText(String.valueOf(new Random().nextInt(max_price - min_price) + max_price));


        constraints = new GridBagConstraints();
        JLabel dateInLabel = new JLabel("Date In: ");
        constraints.gridy = 3;
        constraints.gridx = 0;
        add(dateInLabel, constraints);

        MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
        maskFormatter.setPlaceholderCharacter('_');
        constraints = new GridBagConstraints();
        dateIn = new JFormattedTextField(maskFormatter);
        constraints.weightx = 1.0f;
        constraints.gridy = 3;
        constraints.gridx = 1;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        JLabel dateOutLabel = new JLabel("Date out: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(dateOutLabel, constraints);

        constraints = new GridBagConstraints();
        dateOut = new JFormattedTextField(maskFormatter);
        dateOut.setEditable(false);
        constraints.gridy = 4;
        constraints.gridx = 1;
        add(dateOut, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(prevButton, constraints);


        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        add(nextButton, constraints);
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public int getPricePerNight() {
        return Integer.parseInt(priceInInt.getText());
    }

    public JComboBox<String> getHostels() {
        return hotels;
    }
}