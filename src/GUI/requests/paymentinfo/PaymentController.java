package GUI.requests.paymentinfo;

import Server.DataBaseServer;

public class PaymentController {
    PaymentView view;
    PaymentModel model;
    public PaymentController(DataBaseServer server) {
        model = new PaymentModel(server);
        view = new PaymentView(this);


        view.getDateIn().addActionListener(actionEvent -> {
            if (view.getDateOut().isEnabled()) {
                updateData();
            } else {
                view.getDateOut().setEnabled(true);
            }
        });

        view.getDateOut().addActionListener(actionEvent -> {
            updateData();
        });

        view.setVisible(true);
    }

    private void updateData() {
        PaymentData hotel = model.getPaymentHotel(view.getDateIn().getText(), view.getDateOut().getText());
        PaymentData excursion = model.getPaymentExcursion(view.getDateIn().getText(), view.getDateOut().getText());
        PaymentData air = model.getPaymentAir(view.getDateIn().getText(), view.getDateOut().getText());
        int visaProfit = model.getPaymentVisa(view.getDateIn().getText(), view.getDateOut().getText());


        view.payAirplane(air.getMinus(), air.getPlus());
        view.payEx(excursion.getMinus(), excursion.getPlus());
        view.payHotel(hotel.getMinus(), hotel.getPlus());
        view.payVi(visaProfit);

        int predProf = (air.getPlus() + excursion.getPlus() + hotel.getPlus() + visaProfit);
        int predMinus = (air.getMinus() + excursion.getMinus() + hotel.getMinus());
        view.payPred(predMinus, predProf);
    }

    public PaymentModel getModel() {
        return model;
    }
}
