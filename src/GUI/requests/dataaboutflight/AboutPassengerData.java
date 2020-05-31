package GUI.requests.dataaboutflight;

public class AboutPassengerData {
    private int groupNumber;
    private String hostelName;
    private String name;
    private String lastName;
    private String kind;
    private int mark; // cargo id
    private int birk; // statement id
    public AboutPassengerData(int groupNumber, String hostelName, String name, String lastName, String kind, int mark, int birk) {
        this.groupNumber = groupNumber;
        this.hostelName = hostelName;
        this.name = name;
        this.lastName = lastName;
        this.kind = kind;
        this.mark = mark;
        this.birk = birk;
    }

    public AboutPassengerData() {}

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getHotelName() {
        return hostelName;
    }

    public void setHotelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getBirk() {
        return birk;
    }

    public void setBirk(int birk) {
        this.birk = birk;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
