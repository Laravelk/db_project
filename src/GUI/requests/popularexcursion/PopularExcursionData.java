package GUI.requests.popularexcursion;

public class PopularExcursionData {
    private String excursionName;
    private String agencyName;
    PopularExcursionData(String excursionName, String agencyName) {
        this.excursionName = excursionName;
        this.agencyName = agencyName;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
