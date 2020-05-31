package GUI.requests.countRent;

import javax.swing.*;
import java.awt.*;

public class RentView extends JFrame {
    private RentController controller;
    private JLabel info = new JLabel("info");
    RentView(RentController controller) {
        this.controller = controller;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,50);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        info.setText("Процент дохода от общего количества расходов: " + controller.getModel().countRent() + "%");
        add(info, constraints);
    }
}
