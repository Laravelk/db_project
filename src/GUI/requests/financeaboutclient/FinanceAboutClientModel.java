package GUI.requests.financeaboutclient;

import Server.DataBaseServer;

import java.util.LinkedList;

public class FinanceAboutClientModel {
    private DataBaseServer server;
    FinanceAboutClientModel(DataBaseServer server) {
        this.server = server;
    }

    public LinkedList<TransactionData> getExcursionTransactions(int groupID, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.getExcursionClientsTransaction(groupID, isOnlyWork, isOnlyTravel);
    }

    public LinkedList<TransactionData> getTicketTransactions(int groupID, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.getTicketTransaction(groupID, isOnlyWork, isOnlyTravel);
    }

    public LinkedList<TransactionData> getCargoTransactions(int groupID, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.getTransForCargo(groupID, isOnlyWork, isOnlyTravel);
    }

    public LinkedList<TransactionData> getHotelTrans(int groupID, boolean isOnlyWork, boolean isOnlyTravel) {
        return server.getTransForHotel(groupID, isOnlyWork, isOnlyTravel);
    }


}
