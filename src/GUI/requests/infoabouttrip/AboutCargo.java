package GUI.requests.infoabouttrip;

public class AboutCargo {
    private String name;
    private int weight;
    private int count;
    public AboutCargo(String name, int weight, int count) {
        this.name = name;
        this.weight = weight;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
