package by.ziziko;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static Context context;
    public final static String DATABASE_NAME = "STUDENTSDB";


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GROUPS (IDGROUP INTEGER PRIMARY KEY," +
                "FACULTY TEXT," +
                "COURSE INTEGER," +
                "NAME TEXT NOT NULL," +
                "HEAD INTEGER DEFAULT (null)," +
                "FOREIGN KEY (HEAD) REFERENCES STUDENTS(IDSTUDENT) " +
                "on delete cascade on update cascade, " +
                "CONSTRAINT COURSE_CH CHECK(COURSE <= 5)) ;");

        db.execSQL("CREATE TABLE STUDENTS (IDGROUP INTEGER," +
                "IDSTUDENT INTEGER PRIMARY KEY," +
                "NAME TEXT ," +
                "FOREIGN KEY (IDGROUP) REFERENCES GROUPS(IDGROUP));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addGroup(int ID, String faculty, String name, int course, int head) {
        ContentValues cv = new ContentValues();
        System.out.println("MEM");
        cv.put("IDGROUP", ID);
        cv.put("FACULTY", faculty);
        cv.put("NAME", name);
        cv.put("COURSE", course);
        if (head != 0)
            cv.put("HEAD", head);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("GROUPS", null, cv);
        Toast.makeText(context, "Данные добавлены!", Toast.LENGTH_SHORT).show();
    }

    public void addStudent(int IDGROUP, int IDSTUDENT, String NAME) {
        ContentValues cv = new ContentValues();
        cv.put("IDGROUP", IDGROUP);
        cv.put("IDSTUDENT", IDSTUDENT);
        cv.put("NAME", NAME);
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("STUDENTS", null, cv);
        System.out.println("Данные добавлены");
    }

    public Cursor getGroupByID(int ID) {
        String query = "SELECT * FROM GROUPS WHERE IDGROUP = " + ID + ";";
        return this.getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getStudentsID() {
        String query = "SELECT IDSTUDENT FROM STUDENTS";
        return this.getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getStudentByID(int ID) {
        String query = "SELECT * FROM STUDENTS WHERE IDSTUDENT = " + ID + ";";
        return this.getWritableDatabase().rawQuery(query, null);
    }

    public void deleteGroupByID(int ID) {
        this.getWritableDatabase().delete("STUDENTS", "IDGROUP = " + ID, null);
        this.getWritableDatabase().delete("GROUPS", "IDGROUP = " + ID, null);
        Toast.makeText(context, "Данные успешно удалены!", Toast.LENGTH_SHORT).show();
    }

    public void deleteStudentById(int ID) {
        this.getWritableDatabase().delete("STUDENTS", "IDSTUDENT=" + ID, null);
    }

    public void updateGroupByID(int ID, ContentValues cv) {
        this.getWritableDatabase().update("GROUPS", cv, "IDGROUP = " + ID, null);
    }

    public void updateStudentByID(int ID, ContentValues cv) {
        this.getWritableDatabase().update("STUDENTS", cv, "IDSTUDENT = " + ID, null);
    }
}