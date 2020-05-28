package Data;

public class TripData {
    private final int ID;
    private final String dateIn;
    private final String dateOut;
    private final int bookingID;
    private final int clientID;

    public TripData(int id, String dateIn, String dateOut, int bookingID, int clientID) {
        ID = id;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.bookingID = bookingID;

        this.clientID = clientID;
    }

    public int getID() {
        return ID;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getClientID() {
        return clientID;
    }
}
