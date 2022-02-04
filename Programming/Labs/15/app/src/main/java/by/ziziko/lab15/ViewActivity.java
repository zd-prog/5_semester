package by.ziziko.lab15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    TextView textViewForView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);

        textViewForView = findViewById(R.id.text_view_for_view);

        textViewForView.setText(getIntent().getStringExtra("view"));

    }
}