package GUI.mainwindow;

import javax.swing.*;
import java.awt.*;

public class CategoryFilter extends JComponent {
    private JCheckBox travelCheck = new JCheckBox();
    private JCheckBox workCheck = new JCheckBox();

    CategoryFilter() {
        this.setBorder(BorderFactory.createEtchedBorder());

        JLabel travelLabel = new JLabel("Travel: ");
        JLabel workLabel = new JLabel("Work:");

        setLayout(new GridLayout(2, 2, 5, 12));

        add(travelLabel);
        add(travelCheck);
        add(workLabel);
        add(workCheck);
    }

    JCheckBox getTravelCheck() {
        return travelCheck;
    }

    JCheckBox getWorkCheck() {
        return workCheck;
    }
}
