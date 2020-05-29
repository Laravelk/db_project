package GUI.requests.excursionperiod;

import Server.DataBaseServer;

public class ExcursionPeriodController {
    private ExcursionPeriodModel model;
    private ExcursionPeriodView view;
    public ExcursionPeriodController(DataBaseServer server) {
        model = new ExcursionPeriodModel(server);
        view = new ExcursionPeriodView();

        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
                updateCount();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getDateOut().addActionListener(actionEvent -> {
            updateCount();
        });

        view.setVisible(true);
    }

    private void updateCount() {
        int count = model.getCountPerPeriod(view.getDateIn().getText(),
                                            view.getDateOut().getText());
        view.updateLabel(count);
    }
}
