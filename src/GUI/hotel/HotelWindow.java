package GUI.hotel;

import javax.swing.*;
import javax.swing.text.*;
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
    private final JTextField groupField = new JTextField();
    private Checkbox shopBox = new Checkbox("SHOP TOUR");

    private final JLabel priceInInt = new JLabel("");

    HotelWindow(HotelController controller) throws ParseException {
        this.controller = controller;
        setMinimumSize(new Dimension(270, 210));

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
        JLabel label = new JLabel("Group Number: (def is 0) ");
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(label, constraints);

        PlainDocument doc2 = (PlainDocument) groupField.getDocument();
        doc2.setDocumentFilter(new GUI.hotel.DigitFilter());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(groupField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(shopBox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(prevButton, constraints);


        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 8;
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

    public JTextField getGroupField() {
        return groupField;
    }

    public Checkbox getShopBox() {
        return shopBox;
    }
}

class DigitFilter extends DocumentFilter {
    private static final String DIGITS = "\\d+";

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException, BadLocationException {

        if (string.matches(DIGITS)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        if (string.matches(DIGITS)) {
            super.replace(fb, offset, length, string, attrs);
        }
    }
}