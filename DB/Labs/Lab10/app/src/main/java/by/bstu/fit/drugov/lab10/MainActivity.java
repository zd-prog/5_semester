package by.bstu.fit.drugov.lab10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, DataBaseHelper.DATABASE_NAME, null, 1);
    EditText IDGROUP;
    EditText FACULTY;
    EditText COURSE;
    EditText NAME;
    Spinner HEAD;
    Button addGROUP;
    Button addSTUDENT;
    EditText STUDENT_NAME;
    EditText IDSTUDENT;
    EditText FK_IDGROUP;
    ImageButton SearchGroup;
    ImageButton DeleteGroup;
    ImageButton SearchStudent;
    ImageButton DeleteStudent;
    ImageButton editStudent;
    ImageButton editGroup;
    EditText SearchIDGROUP;
    EditText SearchIDSTUDENT;
    TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IDGROUP = findViewById(R.id.editTextIDGROUP);
        FACULTY = findViewById(R.id.editTextFACULTY);
        COURSE = findViewById(R.id.editTextCOURSE);
        NAME = findViewById(R.id.editTextName);
        HEAD = findViewById(R.id.spinnerHEAD);
        addGROUP = findViewById(R.id.buttonAddGROUPS);
        addSTUDENT = findViewById(R.id.buttonaddSTUDENT);
        FK_IDGROUP = findViewById(R.id.editTextFKGROUP);
        IDSTUDENT = findViewById(R.id.editTextSTUDENTID);
        STUDENT_NAME = findViewById(R.id.editTextSTUDENT_NAME);
        SearchGroup = findViewById(R.id.imageButtonSearchIDGROUP);
        DeleteGroup = findViewById(R.id.imageButtonDeleteGROUP);
        SearchStudent = findViewById(R.id.imageButtonSearchStudent);
        DeleteStudent = findViewById(R.id.imageButtonDeleteStudent);
        SearchIDGROUP = findViewById(R.id.editTextIDGROUPActions);
        SearchIDSTUDENT = findViewById(R.id.editTextIDSTUDENTFind);
        searchResult = findViewById(R.id.textViewSearchResult);
        editStudent = findViewById(R.id.imageButtonEditStudent);
        editGroup = findViewById(R.id.imageButtonEditGROUP);

        updateHeadSpinner();

        File f = getDatabasePath("STUDENTSDB");
        try {
            SQLiteDatabase db1 = SQLiteDatabase.openDatabase(f.getPath(), null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"БД создана",Toast.LENGTH_SHORT).show();
        }
        SQLiteDatabase db = MainActivity.this.openOrCreateDatabase("STUDENTSDB",MODE_PRIVATE,null);
        if(db.isOpen())
            Toast.makeText(MainActivity.this,"БД существует",Toast.LENGTH_SHORT).show();
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "STUDENTSDB", null, 1);



        SearchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResult.setText(null);
                Cursor cursor = dbHelper.getStudentByID(Integer.parseInt(SearchIDSTUDENT.getText().toString()));
                if (cursor.getColumnCount() <= 0)
                    searchResult.setText("Нет такого студента :(");
                cursor.moveToFirst();
                int position = 0;
                StringBuilder sb = new StringBuilder();
                while (!cursor.isAfterLast()) {
                    while (position < cursor.getColumnCount()) {
                        sb.append(cursor.getColumnName(position) + ": " + cursor.getString(position) + '\n');
                        System.out.println(position);
                        position++;
                    }
                    searchResult.setText(sb);
                    cursor.moveToNext();
                }
            }
        });

        SearchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResult.setText(null);
                Cursor cursor = dbHelper.getGroupByID(Integer.parseInt(SearchIDGROUP.getText().toString()));
                cursor.moveToFirst();
                int position = 0;
                StringBuilder sb = new StringBuilder();
                while (!cursor.isAfterLast()) {
                    while (position < cursor.getColumnCount()) {
                        sb.append(cursor.getColumnName(position) + ": " + cursor.getString(position) + '\n');
                        System.out.println(position);
                        position++;
                    }
                    searchResult.setText(sb);
                    cursor.moveToNext();
                }
            }
        });

        DeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteStudentById(Integer.parseInt(SearchIDSTUDENT.getText().toString()));
            }
        });

        DeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteGroupByID(Integer.parseInt(SearchIDGROUP.getText().toString()));
            }
        });


        addGROUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addGroup(Integer.parseInt(IDGROUP.getText().toString()),
                        FACULTY.getText().toString(),
                        NAME.getText().toString(),
                        Integer.parseInt(COURSE.getText().toString()), 0);
            }
        });

        addSTUDENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addStudent(Integer.parseInt(FK_IDGROUP.getText().toString()),
                        Integer.parseInt(IDSTUDENT.getText().toString()),
                        STUDENT_NAME.getText().toString());
                updateHeadSpinner();

            }
        });

        editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View editStudent = layoutInflater.inflate(R.layout.edit_student, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(editStudent);

                EditText FK_Group = editStudent.findViewById(R.id.editTextIDGROUPStudentEdit);
                EditText StudentID = editStudent.findViewById(R.id.editTextIDSTUDENTedit);
                EditText StudentName = editStudent.findViewById(R.id.editTextNAMESTUDENTedit);

                Cursor cursor = dbHelper.getStudentByID(Integer.parseInt(SearchIDSTUDENT.getText().toString()));
                cursor.moveToFirst();
                FK_Group.setText(String.valueOf(cursor.getInt(0)));
                StudentID.setText(String.valueOf(cursor.getInt(1)));
                StudentName.setText(cursor.getString(2));

                builder.setCancelable(false)
                        .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues cv = new ContentValues();
                                cv.put("IDGROUP", Integer.parseInt(FK_Group.getText().toString()));
                                cv.put("IDSTUDENT", Integer.parseInt(StudentID.getText().toString()));
                                cv.put("NAME", StudentName.getText().toString());

                                dbHelper.updateStudentByID(Integer.parseInt(SearchIDSTUDENT.getText().toString()), cv);
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View editGroup = layoutInflater.inflate(R.layout.edit_group, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(editGroup);

                EditText idEdit = editGroup.findViewById(R.id.editTextIDGROUPedit);
                EditText faculty = editGroup.findViewById(R.id.editTextFACULTYedit);
                EditText course = editGroup.findViewById(R.id.editTextCOURSEedit);
                EditText name = editGroup.findViewById(R.id.editTextNAMEedit);
                Spinner head = editGroup.findViewById(R.id.spinnerHEADedit);


                Cursor cursor = dbHelper.getGroupByID(Integer.parseInt(SearchIDGROUP.getText().toString()));
                cursor.moveToFirst();
                idEdit.setText(String.valueOf(cursor.getInt(0)));
                faculty.setText(cursor.getString(1));
                course.setText(String.valueOf(cursor.getInt(2)));
                name.setText(cursor.getString(3));
                head.setAdapter(HEAD.getAdapter());

                builder.setCancelable(false)
                        .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues cv = new ContentValues();
                                cv.put("IDGROUP", Integer.parseInt(idEdit.getText().toString()));
                                cv.put("FACULTY", faculty.getText().toString());
                                cv.put("NAME", name.getText().toString());
                                cv.put("COURSE", Integer.parseInt(course.getText().toString()));
                                cv.put("HEAD", Integer.parseInt(head.getSelectedItem().toString()));

                                dbHelper.updateGroupByID(Integer.parseInt(SearchIDGROUP.getText().toString()), cv);
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    public void updateHeadSpinner() {
        Cursor studentsID = dbHelper.getStudentsID();
        List<String> head_id = new ArrayList<>();
        head_id.add("0");
        studentsID.moveToFirst();
        while (!studentsID.isAfterLast()) {
            System.out.println(studentsID.getColumnName(0));
            head_id.add(studentsID.getString(0));
            studentsID.moveToNext();
        }
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, head_id);
        HEAD.setAdapter(adapter);
    }
}