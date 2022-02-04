package by.ziziko;

public class Subject {

    private int id;
    private String subject;

    public Subject(int id, String subject)
    {
        this.id = id;
        this.subject = subject;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
