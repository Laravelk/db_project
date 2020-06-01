package GUI.mainexcursion;

import GUI.mainwindowforflight.DigitFilter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;

public class AddExcursionDialog extends JDialog {
    private MainExcursionModel model;
    private JFormattedTextField date;
    private JComboBox<Integer> rates;
    private JComboBox<String> nameAgency = new JComboBox<>();
    private JTextField title = new JTextField(20);
    private JFormattedTextField priceField = new JFormattedTextField();
    private JButton ok = new JButton("Add");

    AddExcursionDialog(MainExcursionModel model) {
        this.model = model;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(300,300);
        priceField.setSize(100,20);
        rates = getRates();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Date: "), constraints);

        constraints = new GridBagConstraints();
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        constraints = new GridBagConstraints();
        date = new JFormattedTextField(maskFormatter);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(date, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Rate: "), constraints);

        constraints = new GridBagConstraints();
        rates.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(rates, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(new JLabel("Title: "), constraints);

        constraints = new GridBagConstraints();
        title.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(title, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(new JLabel("Name Agency: "), constraints);


        // name agency
        constraints = new GridBagConstraints();
        LinkedList<String> names = model.getAgencyNames();
        for (String name : names) {
            nameAgency.addItem(name);
        }
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(nameAgency, constraints);

        PlainDocument doc = (PlainDocument) priceField.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 9;
        add(new JLabel("Price: "), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 10;
        add(priceField, constraints);



        constraints = new GridBagConstraints();
        ok.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 11;
        add(ok, constraints);
    }

    private JComboBox<Integer> getRates() {
        JComboBox<Integer> integerJComboBox = new JComboBox<>();
        integerJComboBox.addItem(0);
        integerJComboBox.addItem(1);
        integerJComboBox.addItem(2);
        integerJComboBox.addItem(3);
        integerJComboBox.addItem(4);
        integerJComboBox.addItem(5);
        return integerJComboBox;
    }

    public JComboBox<String> getNameAgency() {
        return nameAgency;
    }

    public JFormattedTextField getDate() {
        return date;
    }

    public JComboBox<Integer> getRate() {
        return rates;
    }

    JTextField getTitles() {
        return title;
    }

    public JButton getOk() {
        return ok;
    }

    public JFormattedTextField getPriceField() {
        return priceField;
    }

    public void setPriceField(JFormattedTextField priceField) {
        this.priceField = priceField;
    }
}