package Data;

public class ExcursionData {
    private int agencyID;
    private int excursionID;
    private String excursionName;
    private String date;
    private String title;
    private int rate;
    private int price;

    public ExcursionData(int excursionID, int agencyID,String excursionName, String date, String title, int rate, int price) {
        this.agencyID = agencyID;
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.date = date;
        this.title = title;
        this.rate = rate;
        this.price = price;
    }

    public ExcursionData(){}

    public int getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(int agencyID) {
        this.agencyID = agencyID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return excursionID == ((ExcursionData) obj).excursionID;
    }
}
