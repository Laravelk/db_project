package GUI.requests.dataaboutflight;

public class AboutFlightData {
    private int volume;
    private int weight;
    private int countForPassenger;
    public AboutFlightData(int volume, int weight, int countForPassenger)  {
        this.volume = volume;
        this.weight = weight;
        this.countForPassenger = countForPassenger;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getWeight() {
        return weight;
    }

    public int getCountForPassenger() {
        return countForPassenger;
    }

    public void setCountForPassenger(int countForPassenger) {
        this.countForPassenger = countForPassenger;
    }
}
