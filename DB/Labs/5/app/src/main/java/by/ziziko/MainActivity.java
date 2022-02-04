package by.ziziko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    String fname = "Lab.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!FileWork.ExistBase(fname, super.getFilesDir()))
        {
            file = FileWork.CreateFile(fname, super.getFilesDir());
        }
        else
        {
            file = new File(getFilesDir().toString()+"/"+fname);
        }

        EditText key = findViewById(R.id.key);
        EditText value = findViewById(R.id.value);
        Button buttonSave = findViewById(R.id.buttonSave);
        EditText key2 = findViewById(R.id.key2);
        EditText valueOutput = findViewById(R.id.valueOutput);
        Button buttonOutput = findViewById(R.id.buttonOutput);

        buttonSave.setOnClickListener(v ->
        {
                if (key.getText().toString().length() == 0 || value.getText().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Hash hash = new Hash(file);
                    if (Integer.parseInt(String.valueOf(key.getText().length()))!=5)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Ключ должен содержать 5 символов", Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        String KEY = key.getText().toString();
                        String VALUE = value.getText().toString();

                        hash.WriteValue(KEY, VALUE);
                        key.setText("");
                        value.setText("");
                    }

                }

        });

        buttonOutput.setOnClickListener(v ->
        {
            String KEY = key2.getText().toString();
            Hash hash = new Hash(file);
            String Value = hash.GetValue(KEY).toString();
            valueOutput.setText(Value);
            Toast toast = new Toast(this);
        });
    }
}