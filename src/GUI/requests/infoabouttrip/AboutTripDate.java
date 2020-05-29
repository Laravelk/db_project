package GUI.requests.infoabouttrip;

public class AboutTripDate {
    private String dateIn;
    private String dateOut;
    public AboutTripDate(String dateIn, String dateOut) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }
}
