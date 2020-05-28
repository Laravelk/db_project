package GUI.flight;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FlightWindow extends JFrame {

    private final JButton flightNext = new JButton("Next Flight");
    private final JButton flightPrev = new JButton("Prev Flight");
    private final JButton prevButton = new JButton("Prev");
    private final JButton nextButton = new JButton("Next");

    private JLabel countOfPageLabel;
    private ComboFlightVariants comboFlightVariants;

    private int countOfPage = 0;


    FlightWindow(FlightController controller, String dateInString,
                 String dateOutString, int countOfPage,
                 int PAINT_VARIANT_ON_PAGE)
            throws ParseException {
        setMinimumSize(new Dimension(1200, 450));

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        comboFlightVariants = new ComboFlightVariants(controller.getModel().getTicketData(), PAINT_VARIANT_ON_PAGE);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridheight = 2;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(comboFlightVariants, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(flightPrev, constraints);

        constraints = new GridBagConstraints();
        countOfPageLabel = new JLabel("Number: " + 0 + " of " + countOfPage);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(countOfPageLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridy = 3;
        constraints.gridx = 3;
        add(flightNext, constraints);

        flightPrev.setEnabled(false);
        if (1 == countOfPage) {
            flightNext.setEnabled(false);
        }

        constraints = new GridBagConstraints();
        constraints.gridy = 4;
        constraints.gridx = 0;
        add(prevButton, constraints);

        constraints.gridy = 4;
        constraints.gridx = 3;
        add(nextButton, constraints);
    }

    int getCountOfPage() {
        return countOfPage;
    }

    JButton getFlightNext() {
        return flightNext;
    }

    JButton getPrevButton() {
        return prevButton;
    }

    JButton getNextButton() {
        return nextButton;
    }

    JButton getFlightPrev() {
        return flightPrev;
    }

    JLabel getCountOfPageLabel() {
        return countOfPageLabel;
    }

    ComboFlightVariants getComboFlight() {
        return comboFlightVariants;
    }
}
