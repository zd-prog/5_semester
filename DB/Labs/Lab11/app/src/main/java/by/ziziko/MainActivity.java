package by.ziziko;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    Calendar selectedStart;
    Calendar selectedFinish;

    Spinner spinner;
    EditText editCount;
    TextView textStart;
    TextView textFinish;
    String[] wayToSelect = new String[]{"Дни", "Месяцы", "Года"};

    Button accept;
    Button averageList;
    Button bestStudents;
    Button worstStudents;
    Button averageGroups;
    Button facultiesList;

    Integer enteredValue;

    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDBHelper = new DatabaseHelper(getApplicationContext());
        mDb = mDBHelper.getWritableDatabase();
        mDBHelper.onCreate(mDb);



        editCount = findViewById(R.id.editCount);

        accept = findViewById(R.id.accept);
        averageList = findViewById(R.id.btn1);
        bestStudents = findViewById(R.id.btn2);
        worstStudents = findViewById(R.id.btn3);
        averageGroups = findViewById(R.id.btn4);
        facultiesList = findViewById(R.id.btn5);

        selectedFinish = Calendar.getInstance();
        selectedStart = Calendar.getInstance();

        textFinish = findViewById(R.id.textFinish);
        textFinish.setText(dateFormat.format(selectedFinish.getTime()));
        textStart = findViewById(R.id.textStart);
        textStart.setText(dateFormat.format(selectedStart.getTime()));

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wayToSelect);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        accept.setOnClickListener(view ->
        {
            try {
                enteredValue = Integer.parseInt(String.valueOf(editCount.getText()));
            } catch (NumberFormatException o) {
                dialogs("Введите корректные данные");
                return;
            }
            selectedStart = Calendar.getInstance();
            selectedFinish = Calendar.getInstance();
            if (spinner.getSelectedItem() == "Дни") {
                selectedStart.set(Calendar.DAY_OF_MONTH, selectedStart.get(Calendar.DAY_OF_MONTH) - enteredValue);
            } else {
                if (spinner.getSelectedItem() == "Месяцы") {
                    selectedStart.set(Calendar.MONTH, selectedStart.get(Calendar.MONTH) - enteredValue);
                } else if (spinner.getSelectedItem() == "Года") {
                    selectedStart.set(Calendar.YEAR, selectedStart.get(Calendar.YEAR) - enteredValue);
                }
            }
            textStart.setText(dateFormat.format(selectedStart.getTime()));

        });
        averageList.setOnClickListener(view ->
                dialogs(mDBHelper.averageList(mDb, dateFormat, selectedStart, selectedFinish)));

        bestStudents.setOnClickListener(view ->
                dialogs(mDBHelper.bestStudents(mDb)));

        worstStudents.setOnClickListener(view ->
                dialogs(mDBHelper.worstStudents(mDb, dateFormat, selectedStart, selectedFinish)));

        averageGroups.setOnClickListener(view ->
                dialogs(mDBHelper.averageGroups(mDb, dateFormat, selectedStart, selectedFinish)));

        facultiesList.setOnClickListener(view ->
                dialogs(mDBHelper.facultiesList(mDb, dateFormat, selectedStart, selectedFinish)));

        Button add = findViewById(R.id.addStudent);
        Button delete = findViewById(R.id.deleteStudent);

        TextView textView = findViewById(R.id.textView);
        add.setOnClickListener(view ->
        {
            try {

                mDb.execSQL("INSERT INTO " + "STUDENT" +
                        " (" + "IDGROUP" + "," + "NAME" + "," + "BIRTHDATE" + "," + "ADDRESS" + ") " +
                        " VALUES (5,'Руслан','12.09.2000','ул. Скорины 51-30');");

            }
            catch (Exception ex)
            {
                textView.setText("Не может быть больше 6 студентов");
            }

        });

        delete.setOnClickListener(view ->
        {
            try {

                mDb.execSQL("delete from STUDENT where (NAME = 'Владислав')");
                mDb.execSQL("delete from STUDENT where (NAME = 'Марк')");
                mDb.execSQL("delete from STUDENT where (NAME = 'Ксения')");
                mDb.execSQL("delete from STUDENT where (NAME = 'Антон')");
                mDb.execSQL("delete from STUDENT where (NAME = 'Владимир')");


            }
            catch (Exception ex)
            {
                textView.setText("Не может быть меньше 3 студентов");
            }
        });
    }

    public void dialogs(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}