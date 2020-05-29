package GUI.requests.excursionperiod;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class ExcursionPeriodView extends JFrame {

    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;
    private JLabel label = new JLabel("In country in this period");

    ExcursionPeriodView() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,300);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(label, constraints);
    }

    public void updateLabel(int count) {
        label.setText("Tourists bought excursion in period: " + count);
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public JLabel getLabel() {
        return label;
    }
}
