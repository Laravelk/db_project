package GUI.requests.infoabouthotel;

import GUI.requests.infoabouttrip.InfoAboutController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.LinkedList;

public class InfoAboutHotelView extends JFrame {
    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;
    private LinkedList<String> hotelsName;
    private LinkedList<JLabel> labels = new LinkedList<>();

    InfoAboutHotelView(InfoAboutHotelController controller) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        setSize(200,270);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Date In:"), constraints);

        constraints = new GridBagConstraints();
        dateIn = new JFormattedTextField(maskFormatter);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Date Out:"), constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        dateOut = new JFormattedTextField(maskFormatter);
        dateOut.setEnabled(false);
        add(dateOut, constraints);

        hotelsName = controller.getModel().getHotelsName();

        int gridy = constraints.gridy + 1;
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        for (String name : hotelsName) {
            constraints.gridy = gridy;
            JLabel label = new JLabel(name);
            labels.add(label);
            add(label, constraints);
            gridy++;
        }
    }

    public void updateInfo(LinkedList<Integer> count) {
        int i = 0;
        for (JLabel label : labels) {
            int counter = count.get(i);
            String name = hotelsName.get(i);
            label.setText(name + ": " + counter);
            i++;
        }
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }
}
