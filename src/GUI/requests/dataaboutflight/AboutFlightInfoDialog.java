package GUI.requests.dataaboutflight;

import Data.FlightData;

import javax.swing.*;
import java.awt.*;

public class AboutFlightInfoDialog extends JDialog {
    private AboutFlightController controller;
    AboutFlightInfoDialog(AboutFlightController controller, FlightData data, AboutFlightData aboutFlightData) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(250,100);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        String text;
        if (data.getAirplaneData().isCargoPlane()) {
            text = "Это грузовой рейс";
        } else {
            text = "Это пассажирский рейс";
        }

        JLabel label1 = new JLabel("Количество занятых мест на рейсе " + aboutFlightData.getCountForPassenger());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(label1, constraints);

        JLabel label2 = new JLabel("Вес груза на рейсе: " + aboutFlightData.getWeight());
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(label2, constraints);

        JLabel label3 = new JLabel("Объем груза на рейсе: " + aboutFlightData.getVolume());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(label3, constraints);

        JLabel label4 = new JLabel(text);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(label4, constraints);
    }
}
