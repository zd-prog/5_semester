package com.example.lab6bday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Button button = findViewById(R.id.button);
        EditText date = findViewById(R.id.bday);
        TextView output = findViewById(R.id.output);

        button.setOnClickListener(v ->
        {
            List<Contact> contacts = new ArrayList<>();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "contactsPublic.json");


            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
            } else
            {
                contacts = JSONHelper.importFromJSON(this, file.getAbsolutePath());
            }
            if (date.getText().toString().equals(""))
            {
                Toast toast = Toast.makeText(this, "Введите дату рождения", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {

                if (!ExistBase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file.getName()))
                {
                    Toast toast = Toast.makeText(this, "Контактов не существует, добавьте их в приложении Контакты", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    String result = "";
                    for (int i = 0; i < contacts.size(); i++)
                    {
                        if (date.getText().toString().equals(contacts.get(i).date))
                        {
                            result += contacts.get(i).surname + " " + contacts.get(i).name + " " + contacts.get(i).phone + "\n";
                        }
                    }

                    output.setText(result);
                    date.setText("");
                }
            }
        });

    }

    private void requestPermissionWithRationale() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    private boolean hasPermission() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                }
                break;

            default:
                break;
        }
    }
}