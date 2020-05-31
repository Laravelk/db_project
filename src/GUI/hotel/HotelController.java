package GUI.hotel;

import GUI.Controller.TripController;
import Server.DataBaseServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelController {

    private DataBaseServer server;
    private HotelModel model;
    private HotelWindow view;

    public HotelController(DataBaseServer server, TripController controller) {
        this.server = server;
        model = new HotelModel(server);
        try {
            view = new HotelWindow(this);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        view.getDateIn().addActionListener(actionEvent -> {
            final int countOfDayInMonth = 31;
            final int countOfMountInYear = 12;
            String [] dateInSeparate = view.getDateIn().getText().split("\\.");
            if (countOfDayInMonth >= Integer.parseInt(dateInSeparate[0]) &&
                    countOfMountInYear >= Integer.parseInt(dateInSeparate[1])) {
                view.getDateOut().setEditable(true);
            } else {
                view.getDateIn().setText("");
            }
        });

        view.getHostels().addActionListener(actionEvent -> {
            JComboBox box = (JComboBox)actionEvent.getSource();
            String name = (String) box.getSelectedItem();
            model.setName(name);
        });

        view.getDateOut().addActionListener(actionEvent -> {
            try {
                int summaryPrice = parseSummaryRoomPrice(view.getDateIn().getText(),
                        view.getDateOut().getText(), view.getPricePerNight());
                getModel().setPrice(summaryPrice);
                getModel().setDateIn(view.getDateIn().getText());
                getModel().setDateOut(view.getDateOut().getText());
                view.getNextButton().setEnabled(true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        view.getShopBox().addItemListener(itemEvent -> {
            getModel().setShopTour(view.getShopBox().getState());
        });

        view.getGroupField().addActionListener(actionEvent -> {
            getModel().setGroupNumber(Integer.parseInt(view.getGroupField().getText()));
        });

        view.getPrevButton().addActionListener(actionEvent
                -> {
            controller.changeWindow(controller.getWindowNumber() - 1);
            view.dispose();
        });
        view.getNextButton().addActionListener(actionEvent -> {
                controller.commitWindow(controller.getWindowNumber()  + 1);
                view.dispose();
        });


        view.setVisible(false);
    }

    private int parseSummaryRoomPrice(String dateIn, String dateOut, int pricePerNight)
            throws ParseException {
        int countOfNight = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = dateFormat.parse(dateIn);
        Date date2 = dateFormat.parse(dateOut);

        long milliseconds = date2.getTime() - date1.getTime();

        countOfNight = (int) (milliseconds / (24 * 60 * 60 * 1000));

        return countOfNight * pricePerNight;
    }

    public HotelModel getModel() {
        return model;
    }

    public HotelWindow getView() {
        return view;
    }

    public void clear() {
        model.clear();
    }

    public void setVisible(boolean t) {
        view.setVisible(t);
    }
}
