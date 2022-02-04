package by.ziziko;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.name);
        EditText author = findViewById(R.id.author);
        Button next = findViewById(R.id.accept);



        next.setOnClickListener(v ->
                {
                    Intent SecondPageIntent = new Intent(this, MainActivity2.class);
                    SecondPageIntent.putExtra("name1", String.valueOf(name.getText()));
                    SecondPageIntent.putExtra("author1", author.getText().toString());
                    startActivity((SecondPageIntent));
                });
    }
}