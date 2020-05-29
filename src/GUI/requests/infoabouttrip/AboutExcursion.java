package GUI.requests.infoabouttrip;

public class AboutExcursion {
    private String nameExcursion;
    private String nameAgency;
    public AboutExcursion(String nameExcursion, String nameAgency) {
        this.nameAgency = nameAgency;
        this.nameExcursion = nameExcursion;
    }

    public String getNameExcursion() {
        return nameExcursion;
    }

    public void setNameExcursion(String nameExcursion) {
        this.nameExcursion = nameExcursion;
    }

    public String getNameAgency() {
        return nameAgency;
    }

    public void setNameAgency(String nameAgency) {
        this.nameAgency = nameAgency;
    }
}
