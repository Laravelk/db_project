package GUI.mainwindow;

import GUI.Controller.TripController;
import GUI.requests.countRent.RentController;
import GUI.requests.countofclientbydate.CountDialogController;
import GUI.requests.dataaboutflight.AboutFlightController;
import GUI.requests.excursionperiod.ExcursionPeriodController;
import GUI.requests.financeaboutclient.FinanceAboutClientController;
import GUI.requests.hotellist.HotelRequestController;
import GUI.requests.infoaboutcargo.InfoAboutCargoController;
import GUI.requests.infoabouthotel.InfoAboutHotelController;
import GUI.requests.infoaboutwarehouse.InfoAboutWarehouseController;
import GUI.requests.paymentinfo.PaymentController;
import GUI.requests.popularexcursion.PopularExcursionController;
import GUI.requests.shopclients.ShopClientsController;
import GUI.trip.TripWindowController;
import GUI.addwindow.AddWindow;
import Server.DataBaseServer;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainController {
    private DataBaseServer server;
    private MainWindow view;
    private MainModel model;

    public MainController(DataBaseServer server) {
        this.server = server;
        model = new MainModel(server);
        view = new MainWindow(server, this);

        view.getAddDeclaration().addActionListener(actionEvent -> {
            if (view.isTableSelection()) {
                int clientID = Integer.parseInt(String.valueOf(view.getTable().
                        getModel().getValueAt(view.getTable().getSelectedRow(), 0)));
                TripController tripController = new TripController(server,
                        server.getClientData(clientID));
            }
        });

        view.getAddPeople().addActionListener(actionEvent -> {
            AddWindow addWindow = new AddWindow(server, "AddWindow", false);
            WindowListener listener = new WindowListener() {
                @Override
                public void windowOpened(WindowEvent windowEvent) {

                }

                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    view.updateTable(view.getTable());
                }

                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    view.updateTable(view.getTable());
                }

                @Override
                public void windowIconified(WindowEvent windowEvent) {

                }

                @Override
                public void windowDeiconified(WindowEvent windowEvent) {

                }

                @Override
                public void windowActivated(WindowEvent windowEvent) {

                }

                @Override
                public void windowDeactivated(WindowEvent windowEvent) {
                    view.updateTable(view.getTable());
                }
            };
            addWindow.addWindowListener(listener);
            addWindow.setVisible(true);
        });

        view.getRequestSecond().addActionListener(actionEvent -> {
            HotelRequestController hotelRequestController = new HotelRequestController(server);
        });

        view.getRequestThree().addActionListener(actionEvent -> {
            CountDialogController controller = new CountDialogController(server);
        });

        view.getEditTrip().addActionListener(actionEvent -> {
            if (view.isTableSelection()) {
                int clientID = Integer.parseInt(String.valueOf(view.getTable().
                        getModel().getValueAt(view.getTable().getSelectedRow(), 0)));
                TripWindowController controller =
                        new TripWindowController(server, true, clientID);
            } else {
                TripWindowController controller = new TripWindowController(server,
                        false, 0);
            }
        });

        view.getRequestFive().addActionListener(actionEvent -> {
            InfoAboutHotelController controller = new InfoAboutHotelController(server);
        });

        view.getRequestSix().addActionListener(actionEvent -> {
            ExcursionPeriodController controller = new ExcursionPeriodController(server);
        });

        view.getRequestSeven().addActionListener(actionEvent -> {
            PopularExcursionController controller = new PopularExcursionController(server);
        });

        view.getRequestEight().addActionListener(actionEvent -> {
            AboutFlightController controller = new AboutFlightController(server);
        });

        view.getRequestNine().addActionListener(actionEvent -> {
            InfoAboutWarehouseController controller = new InfoAboutWarehouseController(server);
        });

        view.getRequestTen().addActionListener(actionEvent -> {
            FinanceAboutClientController controller = new FinanceAboutClientController(server);
        });

        view.getRequestEleven().addActionListener(actionEvent -> {
            PaymentController controller = new PaymentController(server);
        });

        view.getRequestTwelve().addActionListener(actionEvent -> {
            InfoAboutCargoController controller = new InfoAboutCargoController(server);
        });

        view.getRequestThirteen().addActionListener(actionEvent -> {
            RentController controller = new RentController(server);
        });

        view.getRequestFourteen().addActionListener(actionEvent -> {
            ShopClientsController controller = new ShopClientsController(server);
        });

        view.getSearchField().addActionListener(actionEvent -> {
            filterArgs();
        });

        view.getCategoryFilter().getTravelCheck().addActionListener(actionEvent -> {
            if (view.getCategoryFilter().getTravelCheck().isSelected()) {
                view.getCategoryFilter().getWorkCheck().setSelected(false);
            }
            view.setTableSelection(false);
            view.setSelectionID(-1);
            view.getTable().getSelectionModel().clearSelection();
            filterArgs();
        });

        view.getCategoryFilter().getWorkCheck().addActionListener(actionEvent -> {
            if (view.getCategoryFilter().getWorkCheck().isSelected()) {
                view.getCategoryFilter().getTravelCheck().setSelected(false);
            }
            view.setTableSelection(false);
            view.getTable().getSelectionModel().clearSelection();
            view.setSelectionID(-1);
            filterArgs();
        });
        view.setResizable(true);
    }

    private void filterArgs() {
        String text = view.getSearchField().getText();
        final char SPACE = ' ';
        int countOfSpace = 0;
        for (char element : text.toCharArray()) {
            if (SPACE == element) {
                countOfSpace++;
            }
        }
        String[] information;
        information = text.split(" ");

        if (1 == information.length) {
            view.filterTableValue(text, null, null, view.getTable());
        }

        if (2 == information.length) {
            view.filterTableValue(information[0], information[1], null, view.getTable());
        }

        if (3 == information.length) {
            view.filterTableValue(information[0], information[1], information[2], view.getTable());
        }

        if (0 == information.length) {
            view.filterTableValue(null, null, null, view.getTable());
        }
    }

    public MainModel getModel() {
        return model;
    }
}
