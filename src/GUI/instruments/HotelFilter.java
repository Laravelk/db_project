package GUI.instruments;

import GUI.requests.hotellist.HotelRequestController;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class HotelFilter extends JComponent {
    private LinkedList<String> hotels;
    private LinkedList<JCheckBox> hotelsCheckBox = new LinkedList<>();

    public HotelFilter(LinkedList<String> hotelsName, HotelRequestController controller) {
        this.hotels = hotelsName;
        this.setBorder(BorderFactory.createEtchedBorder());

        setLayout(new GridLayout(hotelsName.size(), 2, 5, 12));

        for (String hotel : hotels) {
            JCheckBox jCheckBox = new JCheckBox(hotel);
            jCheckBox.setSelected(true);
            add(jCheckBox);
            hotelsCheckBox.add(jCheckBox);
        }
    }

    public Vector<String> getSelectedHotel() {
        Vector<String> hotels = new Vector<>();
        for (JCheckBox box : hotelsCheckBox) {
            if (box.isSelected()) {
                hotels.add(box.getText());
            }
        }
        return hotels;
    }

    public LinkedList<JCheckBox> getCheckBoxes() {
        return hotelsCheckBox;
    }
}
