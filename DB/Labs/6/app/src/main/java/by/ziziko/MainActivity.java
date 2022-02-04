package by.ziziko;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    EditText name;
    EditText surname;
    EditText phone;
    EditText date;
    Button buttonPublic;
    Button buttonPrivate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        buttonPublic = findViewById(R.id.buttonPublic);
        buttonPrivate = findViewById(R.id.buttonPrivate);


        buttonPrivate.setOnClickListener(v ->
        {
            File file = new File(getFilesDir() + "/" + "contactsPrivate.json");
            List<Contact> contacts = new ArrayList<>();
            if (!CheckFields())
            {
                Toast toast = new Toast(this);
                toast.setText("Проверьте введённые данные");
                toast.show();
            }
            else
            {
                if (!ExistBase(getFilesDir(),file.getName()))
                {
                    try {

                        file.setReadable(true);
                        file.createNewFile();
                    } catch (IOException e) {
                        Log.d("Log_06", "Файл " + file.getName() + " не создан" + e.getMessage());
                    }
                }

                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    try {
                        while ((line = bufferedReader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                        Gson gson = new Gson();
                        JSONHelper.DataItems dataItems;
                        if (gson.fromJson(stringBuilder.toString(), JSONHelper.DataItems.class) == null)
                            dataItems = new JSONHelper.DataItems();
                        else
                            dataItems = gson.fromJson(stringBuilder.toString(), JSONHelper.DataItems.class);
                        if(dataItems.getContacts()!=null) {
                            for (Contact contact :
                                    dataItems.getContacts()) {
                                contacts.add(contact);
                            }
                        }
                        Toast toast = Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            Contact contact = new Contact(name.getText().toString(),surname.getText().toString(),phone.getText().toString(),date.getText().toString());
            contacts.add(contact);

            Gson gson = new Gson();
            JSONHelper.DataItems dataItems = new JSONHelper.DataItems();
            dataItems.setContacts(contacts);
            String jsonString = gson.toJson(dataItems);

            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(jsonString.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        buttonPublic.setOnClickListener(v ->
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "contactsPublic.json");
            List<Contact> contacts = new ArrayList<>();
            if (!CheckFields())
            {
                Toast toast = Toast.makeText(this, "Проверьте введённые данные", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                if (!ExistBase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file.getName()))
                {
                    try {

                        if (hasPermission())
                        {
                            file.createNewFile();
                        }
                        else
                        {
                            requestPermissionWithRationale();
                        }

                    } catch (IOException e) {
                        Log.d("Log_06", "Файл " + file.getName() + " не создан" + e.getMessage());
                    }
                }

                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    try {
                        while ((line = bufferedReader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                        Gson gson = new Gson();
                        JSONHelper.DataItems dataItems;
                        if (gson.fromJson(stringBuilder.toString(), JSONHelper.DataItems.class) == null)
                            dataItems = new JSONHelper.DataItems();
                        else
                            dataItems = gson.fromJson(stringBuilder.toString(), JSONHelper.DataItems.class);
                        if(dataItems.getContacts()!=null) {
                            for (Contact contact :
                                    dataItems.getContacts()) {
                                contacts.add(contact);
                            }
                        }
                        Toast toast = Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            Contact contact = new Contact(name.getText().toString(),surname.getText().toString(),phone.getText().toString(),date.getText().toString());
            contacts.add(contact);

            Gson gson = new Gson();
            JSONHelper.DataItems dataItems = new JSONHelper.DataItems();
            dataItems.setContacts(contacts);
            String jsonString = gson.toJson(dataItems);

            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(jsonString.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void requestPermissionWithRationale() 
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(MainActivity.this.findViewById(R.id.content).getRootView(), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        } 
    }

    private void requestPerms()
    {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    private boolean hasPermission() 
    {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }


    private boolean CheckFields()
    {
        if (name.length() == 0)
          return false;
        if (surname.length() == 0)
            return false;
        if (phone.length() == 0)
            return false;
        if (date.getText().length()==0 || Date.valueOf(date.getText().toString()).before(Date.valueOf("1921-09-29")) || Date.valueOf(date.getText().toString()).after(Date.valueOf("2021-09-29")))
            return false;
        return true;
    }

    private boolean ExistBase(File path, String fileName)
    {
        boolean rc = false;
        File f = new File(path, fileName);
        if (rc = f.exists())
            Log.d("Log_02", "Файл " + fileName + "существует");
        else
            Log.d("Log_02", "Файл " + fileName + "не найден");
        return rc;
    }
}