package by.ziziko;

public class Faculty {

    private int id;
    private String name;
    private String dean;
    private String officeTimetable;

    public Faculty()
    {

    }

    public Faculty(int id, String name, String dean, String officeTimetable) {
        this.id = id;
        this.name = name;
        this.dean = dean;
        this.officeTimetable = officeTimetable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    public String getOfficeTimetable() {
        return officeTimetable;
    }

    public void setOfficeTimetable(String officeTimetable) {
        this.officeTimetable = officeTimetable;
    }

    @Override
    public String toString() {
        return name;
    }
}
