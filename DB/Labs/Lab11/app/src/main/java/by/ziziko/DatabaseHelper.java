package by.ziziko;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "university.db";
    static int SCHEMA = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    private static DatabaseHelper instance = null;

    static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("drop table if exists faculty");
        db.execSQL("drop table if exists PROGRESS");
        db.execSQL("drop table if exists GROUPS");
        db.execSQL("drop table if exists STUDENT");
        db.execSQL("drop table if exists SUBJECT");



        db.execSQL("create table faculty " +
                "(IDFACULTY integer primary key autoincrement, FACULTY text not null," +
                "DEAN text not null, OFFICETIMETABLE text)");
        db.execSQL("CREATE TABLE \"GROUPS\" (\n" +
                "\t\"IDGROUP\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"IDFACULTY\"\tINTEGER NOT NULL,\n" +
                "\t\"COURSE\"\tINTEGER NOT NULL,\n" +
                "\t\"NAME\"\tTEXT NOT NULL,\n" +
                "\t\"HEAD\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"HEAD\") REFERENCES \"STUDENT\"(\"IDSTUDENT\"),\n" +
                "\tFOREIGN KEY(\"IDFACULTY\") REFERENCES \"FACULTY\"(\"IDFACULTY\"))");
        db.execSQL("CREATE TABLE \"PROGRESS\" (\n" +
                "\t\"IDSTUDENT\"\tINTEGER NOT NULL,\n" +
                "\t\"IDSUBJECT\"\tINTEGER NOT NULL,\n" +
                "\t\"EXAMDATE\"\tTEXT NOT NULL,\n" +
                "\t\"MARK\"\tINTEGER NOT NULL,\n" +
                "\t\"TEACHER\"\tTEXT NOT NULL,\n" +
                "\tFOREIGN KEY(\"IDSUBJECT\") REFERENCES SUBJECT(IDSUBJECT),\n" +
                "\tFOREIGN KEY(\"IDSTUDENT\") REFERENCES STUDENT(IDSTUDENT)\n" +
                ")");
        db.execSQL("CREATE TABLE \"STUDENT\" (\n" +
                "\t\"IDSTUDENT\"\tINTEGER,\n" +
                "\t\"IDGROUP\"\tINTEGER NOT NULL,\n" +
                "\t\"NAME\"\tTEXT NOT NULL,\n" +
                "\t\"BIRTHDATE\"\tTEXT NOT NULL,\n" +
                "\t\"ADDRESS\"\tTEXT NOT NULL,\n" +
                "\tFOREIGN KEY(\"IDGROUP\") REFERENCES GROUPS(IDGROUP),\n" +
                "\tPRIMARY KEY(\"IDSTUDENT\")\n" +
                ")");
        db.execSQL("CREATE TABLE \"SUBJECT\" (\n" +
                "\t\"IDSUBJECT\"\tINTEGER PRIMARY KEY autoincrement,\n" +
                "\t\"SUBJECT\"\tTEXT NOT NULL)");
        db.execSQL("INSERT INTO " + "FACULTY" +
                " (" + "FACULTY" + "," + "DEAN" + "," + "OFFICETIMETABLE" + ") " +
                " VALUES ('???????????????????????????? ????????????????????','?????????? ?????????????? ????????????????????','????-???? 8:00-16:15');");
        db.execSQL("INSERT INTO " + "FACULTY" +
                " (" + "FACULTY" + "," + "DEAN" + "," + "OFFICETIMETABLE" + ") " +
                " VALUES ('???????????????????? ???????????????????? ?? ??????????????','???????????? ???????? ??????????????????????????','????-???? 8:00-16:15');");
        db.execSQL("INSERT INTO " + "FACULTY" +
                " (" + "FACULTY" + "," + "DEAN" + "," + "OFFICETIMETABLE" + ") " +
                " VALUES ('??????????????????-?????????????????????????? ??????????????????','???????????????????? ???????????? ????????????????????','????-???? 8:00-16:15');");
        db.execSQL("INSERT INTO " + "FACULTY" +
                " (" + "FACULTY" + "," + "DEAN" + "," + "OFFICETIMETABLE" + ") " +
                " VALUES ('?????????????????????????????? ?? ??????????????????????????????????','?????????????? ?????????????? ????????????????????????','????-???? 8:00-16:15');");

        db.execSQL("INSERT INTO " + "STUDENT" +
                " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                " VALUES (1,'??????????????????','27.02.2001','????. ?????????????????????? 21');");
        db.execSQL("INSERT INTO " + "STUDENT" +
                " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                " VALUES (1,'????????','10.12.2000','????. ?????????????????? 10-123');");
        db.execSQL("INSERT INTO " + "STUDENT" +
                " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                " VALUES (2,'????????????','15.05.1999','????. ???????????????? 29-12');");
        db.execSQL("INSERT INTO " + "STUDENT" +
                " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                " VALUES (3,'??????????','03.01.2001','????. ?????????? ???????????? 40-24');");
        db.execSQL("INSERT INTO " + "STUDENT" +
                " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                " VALUES (4,'????????????????','22.02.2003','????. ?????????????????????? 123-32');");


        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('????????');");
        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('??????????');");
        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('????');");
        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('????????');");
        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('??????');");
        db.execSQL("INSERT INTO " + "SUBJECT" +
                " (" + "SUBJECT" + ") " +
                " VALUES ('????????');");

        db.execSQL("INSERT INTO " + "GROUPS" +
                " (" + "IDFACULTY" + "," + "COURSE" + "," + "NAME" + "," + "HEAD" + ") " +
                " VALUES (1,3,'????????????',2);");
        db.execSQL("INSERT INTO " + "GROUPS" +
                " (" + "IDFACULTY" + "," + "COURSE" + "," + "NAME" + "," + "HEAD" + ") " +
                " VALUES (4,4,'????',3);");
        db.execSQL("INSERT INTO " + "GROUPS" +
                " (" + "IDFACULTY" + "," + "COURSE" + "," + "NAME" + "," + "HEAD" + ") " +
                " VALUES (2,2,'????????',4);");
        db.execSQL("INSERT INTO " + "GROUPS" +
                " (" + "IDFACULTY" + "," + "COURSE" + "," + "NAME" + "," + "HEAD" + ") " +
                " VALUES (2,1,'??????',5);");
        db.execSQL("INSERT INTO " + "GROUPS" +
                " (" + "IDFACULTY" + "," + "COURSE" + "," + "NAME" + "," + "HEAD" + ") " +
                " VALUES (3,3,'??????',6);");


        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (1,1,'2020-11-27',5,'??????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (2,1,'2020-06-25',8,'??????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (3,3,'2018-06-22',7,'??????????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (1,4,'2020-11-14',8,'??????????????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (2,4,'2020-11-25',7,'??????????????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (5,5,'2020-11-27',7,'??????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (5,3,'2019-06-22',3,'??????????????');");
        db.execSQL("INSERT INTO " + "PROGRESS" +
                " (" + "IDSTUDENT" + "," + "IDSUBJECT" + "," + "EXAMDATE" + "," + "MARK" + "," + "TEACHER" + ") " +
                " VALUES (4,2,'2019-01-14',2,'??????????');");


        db.execSQL("CREATE INDEX IF NOT EXISTS " + "INDEX_PROGRESS_EXAMDATE" +
                " ON " + "PROGRESS" + " (" + "EXAMDATE" + ")");
        db.execSQL("CREATE INDEX IF NOT EXISTS " + "INDEX_GROUP_FACULTY" +
                " ON " + "GROUPS" + " (" + "IDFACULTY" + ")");


        db.execSQL("CREATE TRIGGER IF NOT EXISTS " + "TRIGGER_MORE_SIX_STUDENTS" +
                " BEFORE INSERT ON " + "STUDENT" +
                " Begin select RAISE (ABORT,'???? ?????????? ???????? ???????????? ?????????? ??????????????????') WHERE (SELECT count(*) FROM STUDENT current WHERE NEW.IDGROUP=current.IDGROUP) >= 6 ;" +
                " END;");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS " + "TRIGGER_LESS_THREE_STUDENTS" +
                " BEFORE DELETE ON " + "STUDENT" +
                " Begin select RAISE (ABORT,'???? ?????????? ???????? ???????????? ???????? ??????????????????') WHERE (SELECT count(*) FROM STUDENT current WHERE OLD.IDGROUP=current.IDGROUP) <= 3 ;" +
                " END;");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS " + "TRIGGER_FOR_CREATE_VIEW" +
                " AFTER INSERT ON " + "STUDENT" +
                " BEGIN " +
                " UPDATE " + "GROUPS" + " SET " + "HEAD" + "=new." + "IDSTUDENT" + " WHERE " + "GROUPS" + "." + "IDGROUP" + "; " +
                " END;");
    }

    public String averageList(SQLiteDatabase db, DateFormat dateFormat, Calendar selectedStart, Calendar selectedFinish)
    {
        Cursor cursor;
        db.execSQL("drop view if exists AverageView");
        db.execSQL("create view AverageView as " +
                "select STUDENT.NAME, AVG(PROGRESS.MARK) " +
                "from STUDENT inner join PROGRESS on PROGRESS.IDSTUDENT = STUDENT.IDSTUDENT " +
                " where (strftime('%s', PROGRESS.EXAMDATE) > strftime('%s','" + dateFormat.format(selectedStart.getTime()) + "')) and (" +
                "strftime('%s', PROGRESS.EXAMDATE) < strftime('%s','" + dateFormat.format(selectedFinish.getTime()) + "'))" +
                " group by (STUDENT.NAME)", new String[]{});
        cursor = db.rawQuery("select * " +
                "from AverageView", null);
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst())
            do {
                result.append(cursor.getString(0)).append(" ").append(cursor.getDouble(1)).append('\n');
            } while (cursor.moveToNext());

        return result.toString();
    }

    public String bestStudents(SQLiteDatabase db)
    {
        Cursor cursor;
        db.execSQL("drop view if exists BestView");
        db.execSQL("create view BestView as " +
                "select GROUPS.NAME, STUDENT.NAME, AVG(PROGRESS.MARK)" +
                "from STUDENT inner join PROGRESS on PROGRESS.IDSTUDENT = STUDENT.IDSTUDENT" +
                " inner join GROUPS on STUDENT.IDGROUP = GROUPS.IDGROUP" +
                " group by GROUPS.IDGROUP", new String[]{});
        cursor = db.rawQuery("select * " +
                "from BestView", null);
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst())
            do {
                result.append(cursor.getInt(0)).append("  ").append(cursor.getString(1)).append("(").append(cursor.getDouble(2)).append(")\n");
            } while (cursor.moveToNext());

        return result.toString();
    }

    public String worstStudents(SQLiteDatabase db, DateFormat dateFormat, Calendar selectedStart, Calendar selectedFinish)
    {
        Cursor cursor;
        db.execSQL("drop view if exists WorstView");
        db.execSQL("create view WorstView as " +
                "select STUDENT.NAME, SUBJECT.SUBJECT, PROGRESS.MARK" +
                " from STUDENT inner join PROGRESS on STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT " +
                "inner join SUBJECT on PROGRESS.IDSUBJECT = SUBJECT.IDSUBJECT" +
                " where (strftime('%s', PROGRESS.EXAMDATE) > strftime('%s','" + dateFormat.format(selectedStart.getTime()) + "')) and (" +
                "strftime('%s', PROGRESS.EXAMDATE) < strftime('%s','" + dateFormat.format(selectedFinish.getTime()) + "'))" +
                " and (PROGRESS.MARK < 4) ", new String[]{});
        cursor = db.rawQuery("select * " +
                "from WorstView", null);
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst())
            do {
                result.append(cursor.getString(0)).append(" ").append(cursor.getString(1)).append("(").append(cursor.getInt(2)).append(")\n");
            } while (cursor.moveToNext());

        return result.toString();
    }

    public String averageGroups(SQLiteDatabase db, DateFormat dateFormat, Calendar selectedStart, Calendar selectedFinish)
    {
        Cursor cursor;
        db.execSQL("drop view if exists AverageGroupsView");
        db.execSQL("create view AverageGroupsView as " +
                "select SUBJECT.SUBJECT, GROUPS.NAME, AVG(PROGRESS.MARK) " +
                "from SUBJECT inner join PROGRESS on SUBJECT.IDSUBJECT = PROGRESS.IDSUBJECT " +
                "inner join STUDENT on STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT " +
                "inner join GROUPS on GROUPS.IDGROUP = STUDENT.IDGROUP " +
                " where (strftime('%s', PROGRESS.EXAMDATE) > strftime('%s','" + dateFormat.format(selectedStart.getTime()) + "')) and (" +
                "strftime('%s', PROGRESS.EXAMDATE) < strftime('%s','" + dateFormat.format(selectedFinish.getTime()) + "'))" +
                " group by SUBJECT.IDSUBJECT, GROUPS.NAME", new String[]{});
        cursor = db.rawQuery("select * " +
                "from AverageGroupsView", null);
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst())
            do {
                result.append("\t").append(cursor.getString(0)).append(" ").append(cursor.getInt(1)).append("(").append(cursor.getDouble(2)).append(")\n");
            } while (cursor.moveToNext());

        return result.toString();
    }

    public String facultiesList(SQLiteDatabase db, DateFormat dateFormat, Calendar selectedStart, Calendar selectedFinish)
    {
        Cursor cursor;
        db.execSQL("drop view if exists FacultiesView");
        db.execSQL("create view FacultiesView as " +
                "select FACULTY.FACULTY, AVG(PROGRESS.MARK)" +
                " from STUDENT inner join PROGRESS on STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT " +
                "inner join GROUPS on GROUPS.IDGROUP = STUDENT.IDGROUP " +
                "inner join FACULTY on FACULTY.IDFACULTY = GROUPS.IDFACULTY " +
                " where (strftime('%s', PROGRESS.EXAMDATE) > strftime('%s','" + dateFormat.format(selectedStart.getTime()) + "')) and (" +
                "strftime('%s', PROGRESS.EXAMDATE) < strftime('%s','" + dateFormat.format(selectedFinish.getTime()) + "'))" +
                " group by FACULTY.FACULTY", new String[]{});
        cursor = db.rawQuery("select * " +
                "from FacultiesView", null);
        StringBuilder result = new StringBuilder();
        if (cursor.moveToFirst())
            do {
                result.append(cursor.getString(0)).append("\n\t").append("?????????????? ??????: ").append(cursor.getDouble(1)).append('\n');
            } while (cursor.moveToNext());

        return result.toString();
    }
}