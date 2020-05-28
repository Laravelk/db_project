package Data;

public class AirplaneData {
    private int ID;
    private int seatCount;
    private int cargoWeight;
    private int volumeWeight;
    private boolean isCargoPlane;

    public AirplaneData(int id, int seatCount, int cargoWeight, int volumeWeight, String isCargoPlane) {
        this.seatCount = seatCount;
        this.cargoWeight = cargoWeight;
        this.volumeWeight = volumeWeight;
        ID = id;
        if (isCargoPlane.equals("1")) {
        this.isCargoPlane = true;
        }
        else {
            this.isCargoPlane = false;
        }
    }

    public AirplaneData(){}

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public int getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(int volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public boolean isCargoPlane() {
        return isCargoPlane;
    }

    public void setCargoPlane(boolean cargoPlane) {
        isCargoPlane = cargoPlane;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
