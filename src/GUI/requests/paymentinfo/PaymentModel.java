package GUI.requests.paymentinfo;

import Server.DataBaseServer;

public class PaymentModel {
    private DataBaseServer server;
    PaymentModel(DataBaseServer server) {
        this.server = server;
    }

    public PaymentData getPaymentHotel(String dateIn, String dateOut) {
        return server.getHostelPaymentData(dateIn, dateOut);
    }

    public PaymentData getPaymentAir(String dateIn, String dateOut) {
        return server.getAirplanePaymentData(dateIn, dateOut);
    }

    public PaymentData getPaymentExcursion(String dateIn, String dateOut) {
        return server.getExcursionPaymentData(dateIn, dateOut);
    }

    public int getPaymentVisa(String dateIn, String dateOut) {
        return server.getVisaProfit(dateIn, dateOut);
    }
}
