package by.ziziko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fname = "Base_Lab.txt";
        Button button = findViewById(R.id.button);
        TextView surname = findViewById(R.id.surname);
        TextView name = findViewById(R.id.name);
        TextView output = findViewById(R.id.output);

        if (!ExistBase(fname))
        {

            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Создаётся файл " + fname).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("Log_02", "Создание файла " + fname);
                }
            });
            AlertDialog ad = b.create();
            ad.show();

            File f = new File(super.getFilesDir(), fname);
            try
            {
                f.createNewFile();
                Log.d("Log_02", "Файл " + fname + "создан");
            }
            catch (IOException e)
            {
                Log.d("Log_02", "Файл " + fname + "не создан");
            }

        }

        else
        {
            Read(fname, output);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = surname.getText() + ";" + name.getText() + ";" + "\r\n";
                try {
                    FileOutputStream outputStream = openFileOutput(fname, MODE_APPEND);
                    outputStream.write(s.getBytes());
                    outputStream.close();
                }
                catch (IOException e)
                {
                    Log.d("Log_02", "Текст в файл " + fname + "не записан " + e.getMessage());
                }
                surname.setText("");
                name.setText("");
                Read(fname, output);

            }
        });
    }

    private boolean ExistBase(String fname)
    {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        if (rc = f.exists())
            Log.d("Log_02", "Файл " + fname + "существует");
        else
            Log.d("Log_02", "Файл " + fname + "не найден");
        return rc;
    }

    private void Read(String fname, TextView output)
    {
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(fname);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String text = new String(bytes);
            output.setText(text);
        }
        catch (IOException e)
        {
            Log.d("Log_02", "Файл " + fname + " не прочитан " + e.getMessage());
        }
        finally {
            try {
                if (inputStream!=null)
                    inputStream.close();
            }
            catch (IOException e)
            {
                Log.d("Log_02", "Поток " + fname + " не закрыт " + e.getMessage());
            }
        }
    }
}