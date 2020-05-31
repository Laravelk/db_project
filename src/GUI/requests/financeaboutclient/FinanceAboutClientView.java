package GUI.requests.financeaboutclient;

import GUI.instruments.CategoryFilter;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class FinanceAboutClientView extends JFrame {
    private FinanceAboutClientController controller;
    private JTextField groupField = new JTextField(20);
    private CategoryFilter categoryFilter = new CategoryFilter();

    private final String ex = "Потратили на экскурсии: ";
    private final String ho = "Потратили на отели: ";
    private final String ca = "Потратили на услуги с грузом: ";
    private final String ti = "Потратили на билеты: ";

    private JLabel excursion = new JLabel(ex);
    private JLabel hotels = new JLabel(ho);
    private JLabel cargo = new JLabel(ca);
    private JLabel ticket = new JLabel(ti);
    private int value = -1;
    private boolean isOnlyWork = false;
    private boolean isOnlyTravel = false;

    public FinanceAboutClientView(FinanceAboutClientController controller) {
        this.controller = controller;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(300,200);

        GridBagConstraints constraints = new GridBagConstraints();
        JLabel groupLabel = new JLabel("Insert Group: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(groupLabel, constraints);

        PlainDocument doc = (PlainDocument) groupField.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(groupField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(categoryFilter, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(excursion, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(hotels, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(cargo, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(ticket, constraints);
    }

    public JTextField getGroupField() {
        return groupField;
    }

    public CategoryFilter getCategoryFilter() {
        return categoryFilter;
    }
    public boolean isOnlyWork() {
        return isOnlyWork;
    }

    public boolean isOnlyTravel() {
        return isOnlyTravel;
    }

    public void setOnlyTravel(boolean onlyTravel) {
        isOnlyTravel = onlyTravel;
    }

    public void setOnlyWork(boolean onlyWork) {
        isOnlyWork = onlyWork;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public JLabel getExcursion() {
        return excursion;
    }

    public void setExcursion(JLabel excursion) {
        this.excursion = excursion;
    }

    public JLabel getHotels() {
        return hotels;
    }

    public void setHotels(JLabel hotels) {
        this.hotels = hotels;
    }

    public JLabel getCargo() {
        return cargo;
    }

    public void setCargo(JLabel cargo) {
        this.cargo = cargo;
    }

    public JLabel getTicket() {
        return ticket;
    }

    public void setTicket(JLabel ticket) {
        this.ticket = ticket;
    }

    public void payExcursion(int sum) {
        excursion.setText(ex + sum);
    }

    public void payHotel(int sum) {
        hotels.setText(ho + sum);
    }

    public void payCargo(int sum) {
        cargo.setText(ca + sum);
    }

    public void payTicket(int sum) {
        ticket.setText(ti + sum);
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
