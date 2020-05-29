package GUI.requests.countofclientbydate;

import GUI.instruments.CategoryFilter;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class CountDialogView extends JFrame {
    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;
    private JLabel label = new JLabel("In country in this period");
    private CategoryFilter categoryFilter = new CategoryFilter();

    private boolean isOnlyWork = false;
    private boolean isOnlyTravel = false;

    CountDialogView(CountDialogController controller) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new GridBagLayout());

        setSize(200,180);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GridBagConstraints constraints = new GridBagConstraints();
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
        constraints.gridy = 2;
        constraints.gridheight = 2;
        add(categoryFilter, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(label, constraints);
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
}
