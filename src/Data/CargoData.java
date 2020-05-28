package Data;

public class CargoData {
    private String dateIn;
    private String dateOut;
    private String kind;
    private String warehouseID;

    private StatementData statementData = new StatementData();

    public CargoData() {

    }

    public CargoData(String dateIn, String dateOut,
                     String kind, int count, int real_wrap,
                     int cost_insurance) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.kind = kind;
        statementData.setCount(count);
        statementData.setReal_wrap(real_wrap);
        statementData.setCost_insurance(cost_insurance);
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getCount() {
        return statementData.getCount();
    }

    public void setCount(int count) {
        statementData.setCount(count);
    }

    public int getReal_wrap() {
        return statementData.getReal_wrap();
    }

    public void setReal_wrap(int real_wrap) {
       statementData.setReal_wrap(real_wrap);
    }

    public int getCost_insurance() {
        return statementData.getCost_insurance();
    }

    public void setCost_insurance(int cost_insurance) {
        statementData.setCost_insurance(cost_insurance);
    }

    public void setWeight(int weight) {
        statementData.setWeight(weight);
    }

    public int getWeight() {
        return statementData.getWeight();
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }
}