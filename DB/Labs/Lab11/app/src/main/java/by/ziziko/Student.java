package by.ziziko;

public class Student {

    private int id;
    private Group group;
    private String name;
    private String birthdate;
    private String address;

    public Student(int id, Group group, String name, String birthdate, String address)
    {
        this.id = id;
        this.group = group;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
