package by.ziziko;

public class Progress {

    private Student student;
    private Subject subject;
    private String date;
    private int mark;
    private String teacher;

    public Progress(Student student, Subject subject, String date, int mark, String teacher)
    {

        this.student = student;
        this.subject = subject;
        this.date = date;
        this.mark = mark;
        this.teacher = teacher;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
