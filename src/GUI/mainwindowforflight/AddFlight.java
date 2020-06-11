package GUI.mainwindowforflight;

import Data.AirplaneData;
import Data.FlightData;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;

public class AddFlight extends JDialog {
    private JFormattedTextField date = new JFormattedTextField();

    private final JButton ok = new JButton("Ok");
    private FlightData newFlightData = new FlightData();
    private MainModelForFlight model;
    private JComboBox<String> air = new JComboBox<>();
    AddFlight(MainModelForFlight model) {
        this.model = model;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(100,130);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        JLabel seatL = new JLabel("Date: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(seatL, constraints);

        constraints = new GridBagConstraints();
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        date = new JFormattedTextField(maskFormatter);
        add(date, constraints);


        LinkedList<AirplaneData> airplaneData = model.getAllAirplanes();
        LinkedList<String> number = new LinkedList<>();

        for (AirplaneData airplaneData1 : airplaneData) {
            number.add(String.valueOf(airplaneData1.getID()));
        }

        System.out.println(number);
        for (String s : number) {
            air.addItem(s);
        }

        newFlightData.setAirplaneData(airplaneData.getFirst());
        constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        air.setEnabled(false);
        add(air, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(ok, constraints);
    }

    public JButton getOk() {
        return ok;
    }

    public JFormattedTextField getDate() {
        return date;
    }

    public JComboBox<String> getAir() {
        return air;
    }

    public FlightData getNewFlightData() {
        return newFlightData;
    }
}
