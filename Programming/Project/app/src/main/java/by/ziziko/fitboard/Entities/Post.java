package by.ziziko.fitboard.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import by.ziziko.fitboard.Converters.DateConverter;

@TypeConverters({DateConverter.class})
@Entity(tableName = "Post")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @NonNull
    @ColumnInfo(name = "date")
    private Date date;
    @NonNull
    @ColumnInfo(name = "text")
    private String text;
    @NonNull
    @ColumnInfo(name = "author")
    private String author;

    @Ignore
    public Post()
    {

    }

    public Post(String title, String category, Date date, String text, String author) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.text = text;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public void setDate(Date date) {
        this.date =date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
