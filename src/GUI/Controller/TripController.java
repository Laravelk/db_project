package GUI.Controller;

import Data.CargoData;
import Data.ClientData;
import GUI.cargo.CargoController;
import GUI.cargo.CargoModel;
import GUI.excrusion.ExcursionModel;
import GUI.excrusion.ExcursionController;
import GUI.flight.FlightController;
import GUI.flight.FlightModel;
import GUI.hotel.HotelController;
import GUI.hotel.HotelModel;
import Server.DataBaseServer;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TripController {
    private int numberOfWindows = 0;
    private final DataBaseServer server;
    private TripModel tripModel;

    private ExcursionController excursionController;
    private HotelController hotelController;
    private FlightController flightController;
    private CargoController cargoController;

    private static Logger logger = Logger.getLogger(TripController.class.getName());

    public TripController(DataBaseServer server, ClientData client) {
        assert null != server;
        this.server = server;
        numberOfWindows = 1;
        tripModel = new TripModel(server);
        tripModel.setClientData(client);
        initWindow();
    }

    public void changeWindow(int number) {
        numberOfWindows = number;
        initWindow();
    }

    public void commitWindow(int number) {
        commit();
        numberOfWindows = numberOfWindows + 1;
        initWindow();
    }

    public int getWindowNumber() {
        return numberOfWindows;
    }

    private void commit() {
        final int HOSTEL_WINDOW = 1;
        final int CARGO_WINDOW = 2;
        final int FLIGHT_WINDOW = 3;
        final int EXCURSION_WINDOW = 4;

        logger.log(Level.INFO, "Commit window" + numberOfWindows);

        if (HOSTEL_WINDOW == numberOfWindows) {
            HotelModel model = hotelController.getModel();
            logger.log(Level.INFO, "Hotel commit name: " +
                    model.getHotelInfo().getName() + " " + model.getHotelInfo().getDateIn()
                    + " " + model.getHotelInfo().getDateOut());
            tripModel.setBookingInfo(model.getHotelInfo());
        }

        if (CARGO_WINDOW == numberOfWindows) {
            CargoModel model = cargoController.getModel();
            logger.log(Level.INFO, "Cargo commit " +
                    model.getCargoDataLinkedList().getFirst().getKind());
            tripModel.setCargoDataLinkedList(model.getCargoDataLinkedList());
        }

        if (FLIGHT_WINDOW == numberOfWindows) {
            logger.log(Level.INFO, "Flight commit");
            FlightModel model = flightController.getModel();
            tripModel.setTicketData(model.getTicket());
        }

        if (EXCURSION_WINDOW == numberOfWindows) {
            logger.log(Level.INFO, "Excursion commit");
            ExcursionModel model = excursionController.getModel();
            tripModel.setPickedExcursion(model.getExcursions());
        }
    }

    public void initWindow() {
        final int START_WINDOW = 0;
        final int HOSTEL_WINDOW = 1;
        final int CARGO_WINDOW = 2;
        final int FLIGHT_WINDOW = 3;
        final int EXCURSION_WINDOW = 4;
        final int COMMIT_STATE = 5;

        logger.log(Level.INFO, "InitWindow: " + numberOfWindows);

        if (START_WINDOW == numberOfWindows) {
            //firstInit();
        }

        if (HOSTEL_WINDOW == numberOfWindows) {
            hotelController = new HotelController(server, this);
            hotelController.setVisible(true);
        }

        if (CARGO_WINDOW == numberOfWindows) {
            cargoController = new CargoController(server, this,
                    tripModel.getBookingInfo().getDateIn(), tripModel.getBookingInfo().getDateOut());
            cargoController.setVisible(true);
        }

        if (FLIGHT_WINDOW == numberOfWindows) {
            LinkedList<CargoData> data = tripModel.getCargoDataLinkedList();
            flightController = new FlightController(server,
                    this, tripModel.getBookingInfo().getDateIn(),
                    tripModel.getBookingInfo().getDateOut(), data);
            flightController.setVisible(true);
        }

        if (EXCURSION_WINDOW == numberOfWindows) {
            excursionController = new ExcursionController(server, this, tripModel.getBookingInfo().getDateIn(),
                    tripModel.getBookingInfo().getDateOut());
            excursionController.setVisible(true);
        }

        if (COMMIT_STATE == numberOfWindows) {
            tripModel.commitTrip();
        }
    }

    public TripModel getTripModel() {
        return tripModel;
    }
}
