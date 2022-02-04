package by.ziziko;

import java.util.Date;

public class Note {
    String text;
    Date date;
    String category;
    public Note()
    {

    }
    public Note(String text, String category, Date date)
    {
        this.text = text;
        this.date = date;
        this.category = category;
    }

    @Override
    public String toString() {
        return category + ": " + text;
    }
}
