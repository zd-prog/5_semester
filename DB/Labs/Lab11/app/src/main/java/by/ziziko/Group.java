package by.ziziko;

public class Group {

    private int id;
    private Faculty faculty;
    private int course;
    private String name;
    private Student head;

    public Group()
    {

    }

    public Group(int id, Faculty faculty, int course, String name, Student head) {
        this.id = id;
        this.faculty = faculty;
        this.course = course;
        this.name = name;
        this.head = head;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getHead() {
        return head;
    }

    public void setHead(Student head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return name;
    }
}
