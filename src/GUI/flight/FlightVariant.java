package GUI.flight;

import Data.FlightData;
import Data.TicketData;

import javax.swing.*;
import java.awt.*;

// тип судна, дата, номер полетов

// компонент, отображающий один из вариантов полета
public class FlightVariant extends JComponent {
    private final TicketData variant;
    FlightVariant(TicketData variant) {
        this.variant = variant;
        setBorder(BorderFactory.createLineBorder(Color.black));
        int sizeIn = variant.getIn().size();
        int sizeOut = variant.getOut().size();
        GridLayout layout = new GridLayout(sizeIn + sizeOut + 2, 3, 5, 12);


        add(new JLabel("TICKET IN:"));
        add(new JLabel(""));
        add(new JLabel(""));

        int index = 0;
        for (int i = 0; i < sizeIn; i++) {
            FlightData dataIn = variant.getIn().get(index);
            String type;
            if (dataIn.getAirplaneData().isCargoPlane()) {
                type = "CARGO";
            } else {
                type = "PASSENGER";
            }
            add(new JLabel("Type: " + type));
            add(new JLabel("Date: " + dataIn.getData()));
            add(new JLabel( "Flight ID: " + String.valueOf(dataIn.getID())));
            index++;
        }

        add(new JLabel("TICKET OUT:"));
        add(new JLabel(""));
        add(new JLabel(""));

        index = 0;
        for (int i = 0; i < sizeOut; i++) {
            FlightData dataIn = variant.getOut().get(index);
            String type;
            if (dataIn.getAirplaneData().isCargoPlane()) {
                type = "CARGO";
            } else {
                type = "PASSENGER";
            }
            add(new JLabel("Type: " + type));
            add(new JLabel("Date: " + dataIn.getData()));
            add(new JLabel( "Flight ID: " + String.valueOf(dataIn.getID())));
            index++;
        }

        setLayout(layout);
        setVisible(true);
    }

    public void print() {
        System.out.println();
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }

    public TicketData getVariant() {
        return variant;
    }
}
