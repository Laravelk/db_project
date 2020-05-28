package Data;

public class HotelBookingInfo {

    private int price;
    private String dateIn;
    private String dateOut;
    private String name;

    public HotelBookingInfo(int price, String dateIn, String dateOut, String name) {
        this.price = price;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public HotelBookingInfo() {}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
