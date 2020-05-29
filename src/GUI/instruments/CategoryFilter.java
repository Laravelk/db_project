package GUI.instruments;

import javax.swing.*;
import java.awt.*;

public class CategoryFilter extends JComponent {
    private JCheckBox travelCheck = new JCheckBox();
    private JCheckBox workCheck = new JCheckBox();

    public CategoryFilter() {
        this.setBorder(BorderFactory.createEtchedBorder());

        JLabel travelLabel = new JLabel("Travel: ");
        JLabel workLabel = new JLabel("Work:");

        setLayout(new GridLayout(2, 2, 5, 12));

        add(travelLabel);
        add(travelCheck);
        add(workLabel);
        add(workCheck);
    }

    public JCheckBox getTravelCheck() {
        return travelCheck;
    }

    public JCheckBox getWorkCheck() {
        return workCheck;
    }
}
