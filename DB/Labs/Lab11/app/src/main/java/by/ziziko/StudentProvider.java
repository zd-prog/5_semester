package by.ziziko;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StudentProvider extends ContentProvider {

    static final String AUTHORITY = "by.ziziko.providers.StudentsList";
    static final String PATH = "students";

    public static final Uri STUDENT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    static final String STUDENT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH;
    static final String STUDENT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH;

    static final int URI_STUDENTS = 1;
    static final int URI_STUDENTS_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, URI_STUDENTS);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", URI_STUDENTS_ID);
    }

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
// проверяем Uri
        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS: // общий Uri
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "NAME" + " ASC";
                }
                break;
            case URI_STUDENTS_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = "IDSTUDENT" + " = " + id;
                } else {
                    selection = selection + " AND " + "IDSTUDENT" + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("STUDENT", projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                STUDENT_CONTENT_URI);
        return cursor;    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                return STUDENT_CONTENT_TYPE;
            case URI_STUDENTS_ID:
                return STUDENT_CONTENT_ITEM_TYPE;
        }
        return null;    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (uriMatcher.match(uri) != URI_STUDENTS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert("STUDENT", null, contentValues);
        Uri resultUri = ContentUris.withAppendedId(STUDENT_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                break;
            case URI_STUDENTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = "IDSTUDENT" + " = " + id;
                } else {
                    selection = selection + " AND " + "IDSTUDENT" + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete("STUDENT", selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_STUDENTS:
                break;
            case URI_STUDENTS_ID:
                String id = uri.getLastPathSegment();
                Toast.makeText(getContext(), "URI_CONTACTS_ID, " + id, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(selection)) {
                    selection = "IDSTUDENT" + " = " + id;
                } else {
                    selection = selection + " AND " + "IDSTUDENT" + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update("STUDENT", contentValues, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;    }
}
