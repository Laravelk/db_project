package GUI.requests.shopclients;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class ShopClientsView  extends JFrame {
    private ShopClientsController controller;
    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;
    private JLabel info = new JLabel("");
    ShopClientsView(ShopClientsController controller) {
        this.controller = controller;
        setSize(250,100);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setTitle("Введите даты");
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
        add(info, constraints);
    }

    public void updateInfo(int percent) {
        info.setText("Отношение:\n " + percent + "%");
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public JLabel getLabel() {
        return info;
    }
}
