package by.ziziko;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    ArrayList<Book> books = new ArrayList<>();
    final static String NAME = "";
    final static String AUTHOR = "";
    TextView name, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        name = findViewById(R.id.name3);
        author = findViewById(R.id.author3);
        TextView date = findViewById(R.id.date3);
        TextView genre = findViewById(R.id.genre3);
        TextView data = findViewById(R.id.data3);
        Button accept = findViewById(R.id.accept);

        Bundle arguments = getIntent().getExtras();
        name.setText(arguments.get("name2").toString());
        author.setText(arguments.get("author2").toString());
        data.setText(arguments.get("data2").toString());
        genre.setText(arguments.get("genre2").toString());
        if (arguments.get("date2") != null)
            date.setText(arguments.get("date2").toString());
        else
            date.setText("Не прочитано");

        accept.setOnClickListener(v ->
        {
            Book book = new Book(name.getText().toString(), author.getText().toString(), data.getText().toString(), genre.getText().toString(), date.getText().toString());

            Gson gson = new Gson();
            books.add(book);

            JSONHelper.exportToJSON(this, books);
            Toast toast = Toast.makeText(getApplicationContext(), "Записано", Toast.LENGTH_SHORT);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putSerializable(NAME, AUTHOR);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name.setText((CharSequence) savedInstanceState.getSerializable(NAME));
        author.setText((CharSequence) savedInstanceState.getSerializable(AUTHOR));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}