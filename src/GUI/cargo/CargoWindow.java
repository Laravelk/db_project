package GUI.cargo;

import Data.CargoData;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.LinkedList;

public class CargoWindow extends JFrame {
    final private JButton prevButton = new JButton("Previous");
    final private JButton nextButton = new JButton("Next");

    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;
    private JTextField kindField;
    private JTextField costField;
    private JTextField countTextField;
    private JTextField volumeTextField;
    private JButton addCargoButton;
    private JTextField weightTextField;
    private JButton nextWindow;
    private JButton skipButton;
    private JComboBox<String> warehouses;

    CargoWindow(CargoController controller, String dateInString, String dateOutString) throws ParseException {
        setMinimumSize(new Dimension(300, 480));

        CargoData data = new CargoData();
        prevButton.setEnabled(true);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel dateInLabel = new JLabel("Date In: ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(dateInLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
        maskFormatter.setPlaceholderCharacter('_');
        dateIn = new JFormattedTextField(maskFormatter);
        dateIn.setText(dateInString);
        dateIn.setEditable(false);
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 1;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel dateOutLabel = new JLabel("Date out: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 2;
        add(dateOutLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        dateOut = new JFormattedTextField(maskFormatter);
        dateOut.setText(dateOutString);
        dateOut.setEditable(false);
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 3;
        add(dateOut, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel warehouseIDlabel = new JLabel("Warehouse ID: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 4;
        add(warehouseIDlabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        warehouses = new JComboBox<>();
        LinkedList<String> listOfWarehouses = controller.getModel().getWarehouse();
        warehouses.setSize(100, 40);
        for (String id : listOfWarehouses) {
            warehouses.addItem(id);
        }
        controller.getModel().getPrepareData().setWarehouseID(warehouses.getItemAt(0));

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 5;
        add(warehouses, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel kindLabel = new JLabel("Kind: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 6;
        add(kindLabel, constraints);

        constraints = new GridBagConstraints();
        kindField = new JTextField(20);
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 7;
        add(kindField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel cost = new JLabel("Cost: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 8;
        add(cost, constraints);

        constraints = new GridBagConstraints();
        costField = new JTextField(20);
        costField.setEnabled(false);
        PlainDocument doc = (PlainDocument) costField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 9;
        add(costField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel countLabel = new JLabel("Count:");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 10;
        add(countLabel, constraints);

        constraints = new GridBagConstraints();
        countTextField = new JTextField(20);
        countTextField.setEnabled(false);
        PlainDocument doc2 = (PlainDocument) countTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 11;
        add(countTextField, constraints);


        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel weightLabel = new JLabel("Weight:");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 12;
        add(weightLabel, constraints);

        constraints = new GridBagConstraints();
        weightTextField = new JTextField(20);
        weightTextField.setEnabled(false);
        PlainDocument doc3 = (PlainDocument) weightTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 13;
        add(weightTextField, constraints);

        JLabel volumeLabel = new JLabel("Volume: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 14;
        add(volumeLabel, constraints);

        constraints = new GridBagConstraints();
        volumeTextField = new JTextField(20);
        volumeTextField.setEnabled(false);
        PlainDocument doc4 = (PlainDocument) volumeTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 15;
        add(volumeTextField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel costInsurance = new JLabel("Cost insurance is: ");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 16;
        add(costInsurance, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 17;
        add(prevButton, constraints);

        addCargoButton = new JButton("Add another Cargo");
        addCargoButton.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 17;
        add(addCargoButton, constraints);

        nextButton.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 18;
        add(nextButton, constraints);

        skipButton = new JButton("Skip Cargo");
        constraints.gridx = 1;
        constraints.gridy = 18;
        add(skipButton, constraints);
    }

    JTextField getKindField() {
        return kindField;
    }

    JTextField getCostField() {
        return costField;
    }

    JTextField getCountTextField() {
        return countTextField;
    }

    JTextField getWeightTextField() {
        return weightTextField;
    }

    JButton getAddCargoButton() {
        return addCargoButton;
    }

    JButton getNextButton() {
        return nextButton;
    }

    JButton getSkipButton() {
        return skipButton;
    }

    JComboBox<String> getWarehouses() {
        return warehouses;
    }

    JButton getPrevButton() {
        return prevButton;
    }

    public JTextField getVolumeTextField() {
        return volumeTextField;
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

