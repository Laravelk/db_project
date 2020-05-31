package GUI.requests.paymentinfo;

public class PaymentData {
    private int plus;
    private int minus;
    public PaymentData(int plus, int minus) {
        this.plus = plus;
        this.minus = minus;
    }

    public int getPlus() {
        return plus;
    }

    public void setPlus(int plus) {
        this.plus = plus;
    }

    public void setMinus(int minus) {
        this.minus = minus;
    }

    public int getMinus() {
        return minus;
    }
}
