package by.ziziko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView name = findViewById(R.id.name2);
        TextView author = findViewById(R.id.author2);
        EditText Data = findViewById(R.id.Data);
        Spinner genre = findViewById(R.id.genre);
        Switch readed = findViewById(R.id.readed);
        EditText date = findViewById(R.id.date);
        Button next2 = findViewById(R.id.accept);

        String[] genres = {"Роман", "Детектив", "Драма", "Научная фантастика"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre.setAdapter(adapter);


        readed.setOnClickListener(a ->
        {
            if (readed.isChecked())
            {
                date.setVisibility(View.VISIBLE);
            }
            else
            {
                date.setVisibility(View.INVISIBLE);
            }
        });

        next2.setOnClickListener(v ->
        {
            Intent LastPageIntent = new Intent(this, MainActivity3.class);
            LastPageIntent.putExtra("name2", name.getText().toString());
            LastPageIntent.putExtra("author2", author.getText().toString());
            LastPageIntent.putExtra("data2", Data.getText().toString());
            LastPageIntent.putExtra("genre2",genre.getSelectedItem().toString());
            if (readed.isChecked())
                LastPageIntent.putExtra("date2", date.getText().toString());

            startActivity((LastPageIntent));
        });

        Bundle arguments = getIntent().getExtras();
        name.setText(arguments.get("name1").toString());
        author.setText(arguments.get("author1").toString());

    }
}