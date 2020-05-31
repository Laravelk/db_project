package Data;

public class HotelBookingInfo {

    private int price;
    private String dateIn;
    private String dateOut;
    private String name;
    private int groupNumber;
    private boolean isShopTour = false;

    public HotelBookingInfo(int price, String dateIn, String dateOut, String name, int groupNumber, boolean isShopTour) {
        this.price = price;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.groupNumber = groupNumber;
        this.isShopTour = isShopTour;
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

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public boolean isShopTour() {
        return isShopTour;
    }

    public void setShopTour(boolean shopTour) {
        isShopTour = shopTour;
    }
}
