package GUI.requests.infoaboutwarehouse;

public class DataAboutFlightsWithWarehouse {
    private int cargoCount;
    private int passengerCount;
    private int allCount;
    public DataAboutFlightsWithWarehouse(int cargoCount, int passengerCount) {
        this.cargoCount = cargoCount;
        this.passengerCount = passengerCount;
        this.allCount = cargoCount + passengerCount;
    }

    public int getCargoCount() {
        return cargoCount;
    }

    public void setCargoCount(int cargoCount) {
        this.cargoCount = cargoCount;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
