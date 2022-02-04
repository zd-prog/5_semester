package com.example.lab3;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView abs;
    TextView name;
    TextView path;
    TextView readWrite;
    TextView external;
    Button getFilesButton;
    Button getCacheButton;
    Button getExternalButton;
    Button getExternalCacheButton;
    Button getExternalStorageButton;
    Button getExternalPublicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abs = findViewById(R.id.absoluteText2);
        name = findViewById(R.id.name2);
        path = findViewById(R.id.pathText2);
        readWrite = findViewById(R.id.readWriteText2);
        external = findViewById(R.id.externalText2);

        getFilesButton = findViewById(R.id.getFilesButton);
        getCacheButton = findViewById(R.id.getCacheButton);
        getExternalButton = findViewById(R.id.getExternalButton);
        getExternalCacheButton = findViewById(R.id.getExternalCacheButton);
        getExternalStorageButton = findViewById(R.id.getExternalStorageButton);
        getExternalPublicButton = findViewById(R.id.getExternalPublicButton);

        getFilesButton.setOnClickListener(v -> {

            File file = getFilesDir();
            TextWriting(file);
        });

        getCacheButton.setOnClickListener(v -> {

            File file = getCacheDir();
            TextWriting(file);

        });

        getExternalButton.setOnClickListener(v -> {

            File file = getExternalFilesDir(null);
            TextWriting(file);
        });

        getExternalCacheButton.setOnClickListener(v -> {

            File file = getExternalCacheDir();
            TextWriting(file);

        });

        getExternalStorageButton.setOnClickListener(v -> {

            File file = Environment.getExternalStorageDirectory();
            TextWriting(file);

        });

        getExternalPublicButton.setOnClickListener(v -> {

            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            TextWriting(file);

        });
    }

    private void TextWriting(File file) {
        String trueFalseR;
        String trueFalseW;

        abs.setText(file.getAbsolutePath());
        name.setText(file.getName());
        path.setText(file.getPath());
        if (file.canRead())
            trueFalseR = "Да";
        else
            trueFalseR = "Нет";
        if (file.canWrite())
            trueFalseW = "Да";
        else
            trueFalseW = "Нет";
        String str = trueFalseR + "/" + trueFalseW;
        readWrite.setText(str);
        external.setText(Environment.getExternalStorageState());
    }
}