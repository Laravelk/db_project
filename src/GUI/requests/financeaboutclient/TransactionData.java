package GUI.requests.financeaboutclient;

public class TransactionData {
    private int sum;
    private String name;
    private boolean isIncome;

    public TransactionData(int sum, String name, boolean isIncome) {
        this.sum = sum;
        this.name = name;
        this.isIncome = isIncome;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
