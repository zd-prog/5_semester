package by.ziziko;

import java.util.ArrayList;

public class Group {

    private int id;
    private String faculty;
    private int course;
    private String name;
    private Student head;

    public Group()
    {

    }

    public Group(int id, String faculty, int course, String name, Student head) {
        this.id = id;
        this.faculty = faculty;
        this.course = course;
        this.name = name;
        this.head = head;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
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
}

