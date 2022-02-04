package by.ziziko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    MyDBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText id, f, t;
    Button insert, select, selectRaw, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(getApplicationContext());
        id = findViewById(R.id.id);
        f = findViewById(R.id.F);
        t = findViewById(R.id.T);
        insert = findViewById(R.id.insert);
        select = findViewById(R.id.select);
        selectRaw = findViewById(R.id.selectRaw);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        insert.setOnClickListener(v ->
        {
            if (!id.getText().toString().equals("") && !f.getText().toString().equals("") && !t.getText().toString().equals(""))
            {
                ContentValues values = new ContentValues();
                values.put("id", Integer.parseInt(id.getText().toString()));
                values.put("f", Float.parseFloat(f.getText().toString()));
                values.put("t", t.getText().toString());
                long rowId = db.insert("SimpleTable", null, values);
                id.setText("");
                f.setText("");
                t.setText("");
            }
        });

        select.setOnClickListener(v ->
        {
            if (!id.getText().toString().equals(""))
            {
                cursor = db.query("SimpleTable", new String[] {"id", "f", "t"}, "id = ?", new String[]{id.getText().toString()}, null, null, null);
                if (cursor.moveToFirst())
                {
                    id.setText(String.valueOf(cursor.getInt(0)));
                    f.setText(String.valueOf(cursor.getDouble(1)));
                    t.setText(cursor.getString(2));
                }
            }
        });

        selectRaw.setOnClickListener(v ->
        {
            if (!id.getText().toString().equals(""))
            {
                cursor = db.rawQuery("select id, f, t from SimpleTable", null);
                if (cursor.moveToFirst())
                {
                    id.setText(String.valueOf(cursor.getInt(0)));
                    f.setText(String.valueOf(cursor.getDouble(1)));
                    t.setText(cursor.getString(2));
                }
            }
        });

        update.setOnClickListener(v ->
        {
            ContentValues values = new ContentValues();
            values.put("id", Integer.parseInt(id.getText().toString()));
            values.put("f", Float.parseFloat(f.getText().toString()));
            values.put("t", t.getText().toString());
            int c = db.update("SimpleTable", values, "id = ?", new String[] {id.getText().toString()});
            id.setText("");
            f.setText("");
            t.setText("");
        });

        delete.setOnClickListener(v ->
        {
            int ndel = db.delete("SimpleTable", "id = ?", new String[]{id.getText().toString()});
            id.setText("");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = dbHelper.getReadableDatabase();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}