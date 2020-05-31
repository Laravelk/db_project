package GUI.requests.infoaboutcargo;

public class DataAboutCargo {
    private String kind;
    private double count;
    private double volume;
    private double weight;
    public DataAboutCargo(String kind, double count, double volume, double weight) {
        this.kind = kind;
        this.count = count;
        this.volume = volume;
        this.weight = weight;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
