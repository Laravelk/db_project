package GUI.flight;

import Data.CargoData;
import GUI.Controller.TripController;
import Server.DataBaseServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.LinkedList;

public class FlightController {
    private TripController tripController;
    private DataBaseServer server;

    private FlightModel model;
    private FlightWindow view;

    private int numberOfPage = 0;
    private int countOfPage = 0;

    public FlightController(DataBaseServer server, TripController controller,
                            String dateIn, String dateOut, LinkedList<CargoData> cargoData) {
        assert null != server && null != controller && dateIn != null && dateOut != null;
        model = new FlightModel(server, dateIn, dateOut, cargoData);

        final int PAINT_VARIANT_ON_PAGE = 3;

        countOfPage = (int) Math.ceil((double) model.getTicketData().size() / PAINT_VARIANT_ON_PAGE);

        try {
            view = new FlightWindow(this, dateIn, dateOut, countOfPage, PAINT_VARIANT_ON_PAGE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int count_of_page = view.getCountOfPage();

        view.getFlightNext().addActionListener(actionEvent -> {
            view.getFlightPrev().setEnabled(true);
            numberOfPage++;
            view.getCountOfPageLabel().setText("Number: " + (numberOfPage + 1)
                    + " of " + count_of_page);
            view.getComboFlight().insert_from_to(
                    view.getComboFlight().parse_flight_data(model.getTicketData()),
                    numberOfPage * PAINT_VARIANT_ON_PAGE,
                    (numberOfPage + 1) * PAINT_VARIANT_ON_PAGE);
            if (count_of_page == numberOfPage + 1) {
                view.getFlightNext().setEnabled(false);
            }
        });

        view.getNextButton().addActionListener(actionEvent -> {
            final int NO_CHOSEN = -1;
            int chosen = view.getComboFlight().isChosen();
            if (NO_CHOSEN != chosen) {
                model.setTicket(view.getComboFlight().getTicket(chosen));
                controller.commitWindow(numberOfPage);
                view.dispose();
            }
        });

        view.getPrevButton().addActionListener(actionEvent -> {
            controller.changeWindow(controller.getWindowNumber() - 1);
            view.dispose();
        });

        view.getFlightPrev().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.getFlightNext().setEnabled(true);
                numberOfPage--;
                view.getCountOfPageLabel().setText("Number: " + (numberOfPage + 1) + " of " + count_of_page);
                view.getComboFlight().insert_from_to(view.getComboFlight().parse_flight_data(model.getTicketData()),
                        numberOfPage * PAINT_VARIANT_ON_PAGE ,
                        (numberOfPage + 1) * PAINT_VARIANT_ON_PAGE);
                if (0 == numberOfPage) {
                    view.getFlightPrev().setEnabled(false);
                }
            }
        });
    }

    public FlightModel getModel() {
        return model;
    }

    public void setVisible(boolean t) {
        view.setVisible(t);
    }
}
