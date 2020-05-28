package GUI.flight;

import Data.TicketData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

public class ComboFlightVariants extends JComponent {
    private int max_variants = 0;
    private LinkedList<Checkbox> checkboxes = new LinkedList<>();
    private LinkedList<TicketData> tickets = new LinkedList<>();

    ComboFlightVariants(LinkedList<TicketData> data_set, int max_variants) {
        this.max_variants = max_variants;
        LinkedList<FlightVariant> variants = parse_flight_data(data_set);
        init_window(variants);
    }

    private void init_window(LinkedList<FlightVariant> variants) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        int size = 0;
        if (variants.size() < max_variants) {
            size = variants.size();
        } else {
            size = max_variants;
        }
        GridLayout layout = new GridLayout(max_variants, 2, 5, 1);

        insert(variants);

        setLayout(layout);
        setVisible(true);
    }

    public LinkedList<FlightVariant> parse_flight_data(LinkedList<TicketData> data_set) {
        LinkedList<FlightVariant> flight_variants = new LinkedList<>();

        for (TicketData ticketData : data_set) {
            FlightVariant variant = new FlightVariant(ticketData);
            flight_variants.add(variant);
        }

        return flight_variants;
    }

    public void insert_data(LinkedList<FlightVariant> variants) {
        clear();
        insert(variants);
    }

    public void insert(LinkedList<FlightVariant> variants) {
        clear();
        System.out.println("insert variant: " + variants.size());
        int crow = 0;
        if (variants.size() > max_variants) {
            crow = max_variants;
        } else {
            crow = variants.size();
        }
        GridLayout layout = new GridLayout(crow, 2, 5, 0);
        setLayout(layout);

        int index = 0;
        for (FlightVariant variant : variants) {
            if (index == max_variants) {
                break;
            }
            Checkbox checkbox_variant = new Checkbox();
            checkboxes.add(checkbox_variant);
            tickets.add(variant.getVariant());
            checkbox_variant.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent itemEvent) {
                    if (checkbox_variant.getState()) {
                        for (Checkbox checkbox : checkboxes) {
                            if (checkbox_variant != checkbox) {
                                checkbox.setState(false);
                            }
                        }
                    }
                }
            });
            add(checkbox_variant);
            add(variant);
            index++;
            checkbox_variant.setVisible(true);
            variant.setVisible(true);
        }
        repaint();
        revalidate();
    }

    public int isChosen() {
        int index = 0;
        for (Checkbox checkbox : checkboxes) {
            if (checkbox.getState()) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public TicketData getTicket(int index) {
        return tickets.get(index);
    }

    public void insert_from_to(LinkedList<FlightVariant> variants, int from, int to) {
        assert from < to;

        LinkedList<FlightVariant> insert_it = new LinkedList<>();
        int to1 = to;
        int from1 = from;

        if (from1 < 0) {
            from1 = 0;
        }

        if (to1 > variants.size()) {
            to1 = variants.size();
        }
        for (int i = from1; i < to1; i++) {
            insert_it.add(variants.get(i));
        }
        insert(insert_it);
    }

    public void clear() {
        tickets.clear();
        checkboxes.clear();
        removeAll();
    }

    public int getMax_variants() {
        return max_variants;
    }

    public void setMax_variants(int max_variants) {
        this.max_variants = max_variants;
    }
}
