package Data;

public class ClientData {
    public ClientData(String id, String name,
                      String lastName, String lastLastName,
                      String documentID, int age, String childID, String travelTarget) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.lastLastName = lastLastName;
        this.documentID = documentID;
        this.age = age;
        this.childID = childID;
        this.travelTarget = travelTarget;
    }

    private String id;
    private String name;
    private String lastName;
    private String lastLastName;
    private String documentID;
    private String childID;
    private int age;
    private String travelTarget;

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    private String hostel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLastLastName() {
        return lastLastName;
    }

    public void setLastLastName(String lastLastName) {
        this.lastLastName = lastLastName;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTravelTarget() {
        return travelTarget;
    }

    public void setTravelTarget(String travelTarget) {
        this.travelTarget = travelTarget;
    }
}
