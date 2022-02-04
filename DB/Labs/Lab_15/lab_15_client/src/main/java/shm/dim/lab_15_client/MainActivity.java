package shm.dim.lab_15_client;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String AUTHORITY_GROUP = "by.ziziko.fitboard.providers.PostsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get_groups).setOnClickListener(this);
    }


    public String getGroups() {
        String string = "";
        Uri uri = Uri.parse("content://" + AUTHORITY_GROUP + "/groups");

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                string += "ID: " + cursor.getInt(0) + "\n" +
                        "\tКатегория:    " + cursor.getString(1) + "\n" +
                        "\tЗаголовок:         " + cursor.getString(2) + "\n" +
                        "\tАвтор:         " + cursor.getString(5) + "\n" +
                        "\tТекст:         " + cursor.getString(6) + "\n" +
                        "\tДата: " + cursor.getString(4) + "\n\n";
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return string;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_groups:
                Intent intentGroup = new Intent(MainActivity.this, ViewActivity.class);
                intentGroup.putExtra("view", getGroups());
                startActivity(intentGroup);
                break;
        }
    }
}