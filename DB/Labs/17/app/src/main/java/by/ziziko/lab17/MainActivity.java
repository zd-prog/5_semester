package by.ziziko.lab17;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        databaseSQLData databaseData = new databaseSQLData();
        databaseData.execute("");

        Button getData = findViewById(R.id.button);
        getData.setOnClickListener(view ->
                {
                    textView = findViewById(R.id.output);
                    textView.setText(databaseData.Query());
                });

        Button procedure = findViewById(R.id.button2);
        procedure.setOnClickListener(view ->
        {
            textView = findViewById(R.id.output);
            textView.setText(databaseData.Procedure());
        });
        Button function = findViewById(R.id.button3);
        function.setOnClickListener(view ->
        {
            textView = findViewById(R.id.output);
            textView.setText(databaseData.Function());
        });
        Button batch = findViewById(R.id.button4);
        batch.setOnClickListener(view ->
        {
            textView = findViewById(R.id.output);
            textView.setText(databaseData.Batch());
        });
        Button update = findViewById(R.id.button5);
        update.setOnClickListener(view ->
        {
            textView = findViewById(R.id.output);
            textView.setText(databaseData.Update());
        });
        Button delete = findViewById(R.id.button6);
        delete.setOnClickListener(view ->
        {
            textView = findViewById(R.id.output);
            textView.setText(databaseData.Delete());
        });    }
}