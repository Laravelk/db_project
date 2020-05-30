package GUI.requests.infoaboutwarehouse;

public class DataAboutWarehouse {
    private int weightSum;
    private int count;
    public DataAboutWarehouse(int weightSum, int count) {
        this.weightSum = weightSum;
        this.count = count;
    }

    public int getWeightSum() {
        return weightSum;
    }

    public void setWeightSum(int weightSum) {
        this.weightSum = weightSum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
