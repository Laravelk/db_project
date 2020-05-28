package Data;

public class FlightData {

    private int ID;
    private String date;
    private AirplaneData airplaneData;

    public FlightData(int id, String date, AirplaneData airplaneData) {
        this.ID = id;
        this.date = date;
        this.airplaneData = airplaneData;
    }

    public FlightData() {}

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public AirplaneData getAirplaneData() {
        return airplaneData;
    }

    public void setAirplaneData(AirplaneData airplaneData) {
        this.airplaneData = airplaneData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            FlightData data = (FlightData) obj;
            return this.getAirplaneData().getID() == data.getAirplaneData().getID() && this.getData().equals(((FlightData) obj).getData()) &&
                    this.getID() == data.getID();
        }
        return false;
    }
}
