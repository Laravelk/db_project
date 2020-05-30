package GUI.mainwindow;

import GUI.Controller.TripController;
import GUI.requests.countofclientbydate.CountDialogController;
import GUI.requests.excursionperiod.ExcursionPeriodController;
import GUI.requests.hotellist.HotelRequestController;
import GUI.requests.infoabouthotel.InfoAboutHotelController;
import GUI.requests.popularexcursion.PopularExcursionController;
import GUI.trip.TripWindowController;
import GUI.addwindow.AddWindow;
import Server.DataBaseServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                TripController tripController = new TripController(server,
                        server.getClientData(view.getSelectionID()));
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
                TripWindowController controller =
                        new TripWindowController(server, true, view.getSelectionID());
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

        view.getSearchField().addActionListener(actionEvent -> {
            filterArgs();
        });

        view.getCategoryFilter().getTravelCheck().addActionListener(actionEvent -> {
            if (view.getCategoryFilter().getTravelCheck().isSelected()) {
                view.getCategoryFilter().getWorkCheck().setSelected(false);
            }
            filterArgs();
        });

        view.getCategoryFilter().getWorkCheck().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (view.getCategoryFilter().getWorkCheck().isSelected()) {
                    view.getCategoryFilter().getTravelCheck().setSelected(false);
                }
                view.setTableSelection(false);
                view.getTable().getSelectionModel().clearSelection();
                filterArgs();
            }
        });
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

        if (0 == information.length) {
            view.filterTableValue(null, null, null, view.getTable());
        }

        if (1 == information.length) {
            view.filterTableValue(text, null, null, view.getTable());
        }

        if (2 == information.length) {
            view.filterTableValue(information[0], information[1], null, view.getTable());
        }

        if (3 == information.length) {
            view.filterTableValue(information[0], information[1], information[2], view.getTable());
        }
    }

    public MainModel getModel() {
        return model;
    }
}
