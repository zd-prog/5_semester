package by.ziziko;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    ArrayAdapter<Student> arrayAdapter;
    Spinner spinner;
    List<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this, "university", null, 1);

        mDb = mDBHelper.getWritableDatabase();

        spinner = findViewById(R.id.groups);
        groups = getGroups();

        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groups);
        spinner.setAdapter(adapter);

        Button output = findViewById(R.id.outputButton);
        ListView listView = findViewById(R.id.list);
        List<Student> students = new ArrayList<>();
        output.setOnClickListener(v ->
        {
            students.removeAll(students);
            getStudents(spinner, groups, students);
            arrayAdapter = new ArrayAdapter<Student>(this, R.layout.list_item, students);
            listView.setAdapter(arrayAdapter);
        });

        listView.setClickable(true);
        listView.setOnItemClickListener((adapterView, view, i, l) ->
        {
            Student student = students.get(i);
            Dialog itemDialog = new Dialog(this);
            itemDialog.setContentView(R.layout.item_dialog);
            EditText name = itemDialog.findViewById(R.id.nameStudent);
            name.setText(student.getName());
            Button change = itemDialog.findViewById(R.id.changeButton);
            Button delete = itemDialog.findViewById(R.id.deleteButton);
            itemDialog.show();

            change.setOnClickListener(v ->
            {

                ContentValues cv = new ContentValues();
                cv.put("NAME", name.getText().toString());
                mDb.update("STUDENTS", cv, "STUDENTID = ?", new String[] {String.valueOf(student.getId())});
                itemDialog.cancel();
                students.removeAll(students);
                getStudents(spinner, groups, students);
                arrayAdapter.notifyDataSetChanged();
            });

            delete.setOnClickListener(v ->
            {
                mDb.delete("STUDENTS", "STUDENTID = ?", new String[]{String.valueOf(student.getId())});
                students.removeAll(students);
                itemDialog.cancel();
                getStudents(spinner, groups, students);
                arrayAdapter.notifyDataSetChanged();
            });
        });


        Button addGroup = findViewById(R.id.addGroupButton);
        Button addStudent = findViewById(R.id.addStudentButton);
        Button deleteGroup = findViewById(R.id.deleteGroupButton);
        Button allStudents = findViewById(R.id.allStudents);

        allStudents.setOnClickListener(view ->
        {
            students.removeAll(students);
            Toast.makeText(this, getAllStudents(), Toast.LENGTH_LONG).show();
        });

        deleteGroup.setOnClickListener(view ->
        {
            Group group = new Group();
            mDb.delete("GROUPS", "NAME = ?", new String[]{String.valueOf(spinner.getSelectedItem())});
            for (Group g: groups)
            {
                if (g.getName().equals(spinner.getSelectedItem().toString()))
                    group = g;
            }
            groups.remove(group);
            for (Student s: students)
            {
                if (s.getGroup().getId() == group.getId())
                {
                    students.remove(s);
                    mDb.delete("STUDENTS", "GROUPID = ?", new String[]{String.valueOf(s.getGroup().getId())});
                }

            }
            adapter.notifyDataSetChanged();
        });

        addGroup.setOnClickListener(v ->
        {
            Dialog addGroupDialog = new Dialog(this);
            addGroupDialog.setContentView(R.layout.add_group_dialog);
            EditText faculty = addGroupDialog.findViewById(R.id.faculty);
            EditText course = addGroupDialog.findViewById(R.id.course);
            EditText name = addGroupDialog.findViewById(R.id.name);
            Button addGroupButton = addGroupDialog.findViewById(R.id.groupAddButton);

            addGroupDialog.show();
            addGroupButton.setOnClickListener(view ->
            {
                if (!faculty.equals("") && !course.equals("") && !name.equals(""))
                {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("FACULTY", faculty.getText().toString());
                    contentValues.put("COURSE", course.getText().toString());
                    contentValues.put("NAME", name.getText().toString());
                    mDb.insert("GROUPS", null, contentValues);
                    groups = getGroups();
                    addGroupDialog.cancel();
                }
                adapter.notifyDataSetChanged();
                Intent addservice1 = new Intent(MainActivity.this,MainActivity.class);
                startActivity(addservice1);
            });
        });

        addStudent.setOnClickListener(view ->
        {
            Dialog addStudentDialog = new Dialog(this);
            addStudentDialog.setContentView(R.layout.add_student_dialog);
            Spinner spinn = addStudentDialog.findViewById(R.id.groupsStudent);
            EditText name = addStudentDialog.findViewById(R.id.studentName);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_item, groups);
            spinn.setAdapter(adapter);
            Button button = addStudentDialog.findViewById(R.id.studentAddButton);
            addStudentDialog.show();

            button.setOnClickListener(view1 ->
            {
                if (!name.equals("") && !spinn.getSelectedItem().toString().equals(""))
                {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("NAME", name.getText().toString());
                    Group group = new Group();
                    for (Group g: groups) {
                        if(g.getName().equals(spinn.getSelectedItem().toString()))
                            group = g;
                    }
                    contentValues.put("GROUPID", group.getId());
                    mDb.insert("STUDENTS", null, contentValues);
                    addStudentDialog.cancel();
                    arrayAdapter.notifyDataSetChanged();
                }
            });

        });
    }

    private void getStudents(Spinner spinner, List<Group> groups, List<Student> students) {
        Cursor studentsCursor = mDb.rawQuery("select * from STUDENTS", null);
        studentsCursor.moveToFirst();
        Group group = new Group();
        for (Group g: groups) {
            if (g.getName().equals(spinner.getSelectedItem().toString()))
            {
                group = g;
            }
        }
        while (!studentsCursor.isAfterLast())
        {
            if (studentsCursor.getInt(0)==group.getId())
                students.add(new Student(group, studentsCursor.getInt(1), studentsCursor.getString(2)));
            studentsCursor.moveToNext();
        }
        studentsCursor.close();
    }

    private String getAllStudents()
    {
        String str = "";
        Cursor studentsCursor = mDb.rawQuery("select * from STUDENTS", null);
        studentsCursor.moveToFirst();
        while (!studentsCursor.isAfterLast())
        {
            str += " " + studentsCursor.getString(2) + '\n';
            studentsCursor.moveToNext();
        }
        studentsCursor.close();
        return str;
    }
    @NonNull
    private List<Group> getGroups() {
        ArrayList<Group>groups = new ArrayList<>();
        Cursor groupsCursor = mDb.rawQuery("select * from GROUPS", null);
        groupsCursor.moveToFirst();
        while (!groupsCursor.isAfterLast())
        {
            groups.add(new Group(groupsCursor.getInt(0), groupsCursor.getString(1), groupsCursor.getInt(2), groupsCursor.getString(3), null));
            groupsCursor.moveToNext();
        }
        groupsCursor.close();
        return groups;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}