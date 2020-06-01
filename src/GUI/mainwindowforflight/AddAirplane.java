package GUI.mainwindowforflight;

import Data.AirplaneData;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class AddAirplane extends JDialog {
    private MainModelForFlight model;
    private JFormattedTextField seatCount = new JFormattedTextField(300);
    private JFormattedTextField cargoWeight = new JFormattedTextField(40000);
    private JFormattedTextField volumeWeight = new JFormattedTextField(50000);
    private JCheckBox cargoPlane = new JCheckBox("CARGO");
    private JButton ok = new JButton("Add");
    AddAirplane(MainModelForFlight model) {
        this.model = model;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(250,200);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Seat Count: "), constraints);

        PlainDocument doc = (PlainDocument) seatCount.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(seatCount, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Cargo Weight: "), constraints);

        PlainDocument doc2 = (PlainDocument) cargoWeight.getDocument();
        doc2.setDocumentFilter(new DigitFilter());

        constraints = new GridBagConstraints();
        cargoWeight.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(cargoWeight, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(new JLabel("Volume Weight: "), constraints);

        PlainDocument doc3 = (PlainDocument) volumeWeight.getDocument();
        doc3.setDocumentFilter(new DigitFilter());

        constraints = new GridBagConstraints();
        volumeWeight.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(volumeWeight, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(cargoPlane, constraints);

        constraints = new GridBagConstraints();
        ok.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(ok, constraints);
    }

    public JCheckBox getCargoPlane() {
        return cargoPlane;
    }

    public JFormattedTextField getCargoWeight() {
        return cargoWeight;
    }

    public JFormattedTextField getSeatCount() {
        return seatCount;
    }

    public JFormattedTextField getVolumeWeight() {
        return volumeWeight;
    }

    public JButton getOk() {
        return ok;
    }
}

