package by.ziziko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Note[] notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Date[] date = new Date[1];

        CalendarView calendar = findViewById(R.id.calendar);
        EditText note = findViewById(R.id.note);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonChange = findViewById(R.id.buttonChange);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        
        calendar.setOnDateChangeListener((calendarView, i, i1, i2) ->
        {
            note.setText("");
            notes = JSONHelper.importFromJSON(this);
            date[0] = new Date(i, i1, i2);
            if (notes == null)
            {
                buttonAdd.setVisibility(View.VISIBLE);
                buttonChange.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
            }
            else
            {
                for (Note n : notes)
                {
                    if (n != null)
                    {
                        if (n.date.equals(date[0]))
                        {
                            buttonChange.setVisibility(View.VISIBLE);
                            buttonDelete.setVisibility(View.VISIBLE);
                            buttonAdd.setVisibility(View.INVISIBLE);
                            note.setText(n.text);
                        }
                        else
                        {
                            buttonAdd.setVisibility(View.VISIBLE);
                            buttonChange.setVisibility(View.INVISIBLE);
                            buttonDelete.setVisibility(View.INVISIBLE);
                        }
                    }
                    else
                    {
                        buttonAdd.setVisibility(View.VISIBLE);
                        buttonChange.setVisibility(View.INVISIBLE);
                        buttonDelete.setVisibility(View.INVISIBLE);
                    }
                }
            }

        });

        buttonAdd.setOnClickListener(v ->
        {
            if (note.getText().toString().equals(""))
            {
                Toast toast = Toast.makeText(this, "Write note", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                Note n = new Note(note.getText().toString(), date[0]);
                if (notes == null)
                {
                    notes = new Note[10];
                }
                    for (int i = 0; i <= notes.length - 1; i++)
                    {
                        if (notes[i] == null)
                        {
                            notes[i] = n;
                        }
                    }
                note.setText("");
                JSONHelper.exportToJSON(this, notes);
            }
        });

        buttonChange.setOnClickListener(v->
        {
            for (int i = 0; i <= notes.length - 1; i++)
            {
                if (notes[i].date.equals(date[0]))
                {
                    notes[i].text = note.getText().toString();
                }
            }
            note.setText("");
            JSONHelper.exportToJSON(this, notes);
        });
        buttonDelete.setOnClickListener(v->
        {
            for (int i = 0; i <= notes.length - 1; i++)
            {
                if (notes[i].date.equals(date[0]))
                {
                    notes[i] = null;
                }
            }
            note.setText("");
            JSONHelper.exportToJSON(this, notes);
            buttonChange.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonAdd.setVisibility(View.VISIBLE);
        });
    }
}