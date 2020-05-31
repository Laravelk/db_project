package GUI.requests.financeaboutclient;

import Server.DataBaseServer;

import java.util.LinkedList;

public class FinanceAboutClientController {
    private FinanceAboutClientModel model;
    private FinanceAboutClientView view;
    public FinanceAboutClientController(DataBaseServer server) {
        model = new FinanceAboutClientModel(server);
        view = new FinanceAboutClientView(this);

        view.getGroupField().addActionListener(actionEvent -> {
            view.setValue(Integer.parseInt(view.getGroupField().getText()));
        });

        view.getCategoryFilter().getTravelCheck().addActionListener(actionEvent -> {
            view.setOnlyTravel(view.getCategoryFilter().getTravelCheck().isSelected());
            view.setOnlyWork(false);
            view.getCategoryFilter().getWorkCheck().setSelected(false);
            updateLabels();
        });

        view.getCategoryFilter().getWorkCheck().addActionListener(actionEvent -> {
            view.setOnlyWork(view.getCategoryFilter().getWorkCheck().isSelected());
            view.setOnlyTravel(false);
            view.getCategoryFilter().getTravelCheck().setSelected(false);
            updateLabels();
        });

        view.setVisible(true);
    }

    private int countSum(LinkedList<TransactionData> linkedList) {
        int sum = 0;
        for (TransactionData data : linkedList) {
            if (data.isIncome()) {
                sum += data.getSum();
            } else {
                sum -= data.getSum();
            }
        }
        return sum;
    }

    void updateLabels() {
        LinkedList<TransactionData> excursion = model.getExcursionTransactions(view.getValue(), view.isOnlyWork(), view.isOnlyTravel());
        LinkedList<TransactionData> hotels = model.getHotelTrans(view.getValue(), view.isOnlyWork(), view.isOnlyTravel());
        LinkedList<TransactionData> ticket = model.getTicketTransactions(view.getValue(), view.isOnlyWork(), view.isOnlyTravel());
        LinkedList<TransactionData> cargo = model.getCargoTransactions(view.getValue(), view.isOnlyWork(), view.isOnlyTravel());

        int excSum = countSum(excursion);
        int hotSum = countSum(hotels);
        int ticSum = countSum(ticket);
        int carSum = countSum(cargo);

        view.payExcursion(excSum);
        view.payCargo(carSum);
        view.payHotel(hotSum);
        view.payTicket(ticSum);
    }

    public FinanceAboutClientModel getModel() {
        return model;
    }
}
