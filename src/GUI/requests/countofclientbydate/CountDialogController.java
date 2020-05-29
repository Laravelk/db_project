package GUI.requests.countofclientbydate;

import Server.DataBaseServer;

public class CountDialogController {
    private CountDialogModel model;
    private CountDialogView view;

    public CountDialogController(DataBaseServer server) {
        view = new CountDialogView(this);
        model = new CountDialogModel(server);

        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
                updateCount();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getCategoryFilter().getTravelCheck().addActionListener(actionEvent -> {
            view.setOnlyTravel(view.getCategoryFilter().getTravelCheck().isSelected());
            view.setOnlyWork(false);
            view.getCategoryFilter().getWorkCheck().setSelected(false);
            updateCount();
        });

        view.getCategoryFilter().getWorkCheck().addActionListener(actionEvent -> {
            view.setOnlyWork(view.getCategoryFilter().getWorkCheck().isSelected());
            view.setOnlyTravel(false);
            view.getCategoryFilter().getTravelCheck().setSelected(false);
            updateCount();
        });

        view.getDateOut().addActionListener(actionEvent -> {
            updateCount();
        });

        view.setVisible(true);
    }

    private void updateCount() {
        int count = model.getCount(view.getDateIn().getText(), view.getDateOut().getText(), view.isOnlyWork(), view.isOnlyTravel());
        view.getLabel().setText("In country in this period: " + count);
    }
}
