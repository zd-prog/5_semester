package by.ziziko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBSimple.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" +
                    DBContract.DBEntry._ID + " INTEGER PRIMARY KEY," +
                    DBContract.DBEntry.COLUMN_NAME_NAME +" TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_CATEGORY +" TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_INGREDIENTS + " TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_TIME + " TEXT" + "," +
                    DBContract.DBEntry.COLUMN_NAME_RECIPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
