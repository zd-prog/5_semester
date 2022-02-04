package by.ziziko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    String[] lifestyles = {"Сидячий образ жизни", "Занятия 1-3 " +
            "раз в неделю", "Занятия 3-5 раз в неделю", "Занятия 6-7 раз в неделю", "Спортивный образ жизни"};

    Button calculateButton;
    TextView calories;
    RadioButton male;
    RadioButton female;
    TextInputEditText kilos;
    TextInputEditText cm;
    TextInputEditText age;
    Double AMR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lifestyles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calculateButton = findViewById(R.id.button);
        calories = findViewById(R.id.calories);
        male = findViewById(R.id.genderM);
        female = findViewById(R.id.genderF);
        kilos = findViewById(R.id.kilos);
        cm = findViewById(R.id.centimetres);
        age = findViewById(R.id.ages);


        calculateButton.setOnClickListener(v -> {

            switch (spinner.getSelectedItem().toString())
            {
                case "Сидячий образ жизни":
                    AMR = 1.2;
                    break;
                case "Занятия 1-3 раз в неделю":
                    AMR = 1.375;
                    break;
                case "Занятия 3-5 раз в неделю":
                    AMR = 1.55;
                    break;
                case "Занятия 6-7 раз в неделю":
                    AMR = 1.725;
                    break;
                case "Спортивный образ жизни":
                    AMR = 1.9;
                    break;

            }

            if (kilos.getText().toString().equals("") || cm.getText().toString().equals("") || age.getText().toString().equals(""))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                if (female.isChecked())
                {
                    calories.setText(String.valueOf(Harris_Benedict((String)female.getText(), Float.parseFloat(String.valueOf(kilos.getText())), Integer.parseInt(String.valueOf(cm.getText())), Integer.parseInt(String.valueOf(age.getText())), AMR )));
                }
                else

                {
                    if (male.isChecked())
                    {
                        calories.setText(String.valueOf(Harris_Benedict((String) male.getText(), Float.parseFloat(String.valueOf(kilos.getText())), Integer.parseInt(String.valueOf(cm.getText())), Integer.parseInt(String.valueOf(age.getText())), AMR)));
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

        });
    }


    protected int Harris_Benedict(String gender, float weight, int height, int age, double AMR) {

        if (gender.equals("Ж"))

        {
            return (int) ((655.0955 + (9.5634 * weight) + (1.8496 * height) - (6.7550 * age)) * AMR);
        }
        else
        {
            return (int) ((655.4730 + (13.7516 * weight) + (5.0033 * height) - (6.7550 * age)) * AMR);
        }

    }
}