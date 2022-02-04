package by.ziziko;

public class Student {
    private Group group;
    private int id;
    private String name;

    public Student()
    {

    }

    public Student(Group group, int id, String name) {
        this.group = group;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
}
