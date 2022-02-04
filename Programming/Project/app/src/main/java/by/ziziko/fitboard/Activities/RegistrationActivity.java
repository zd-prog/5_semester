package by.ziziko.fitboard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.UserViewModel;

public class RegistrationActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);

        EditText login = findViewById(R.id.loginR);
        EditText password = findViewById(R.id.passwordR);
        TextView registration = findViewById(R.id.registrationR);

        registration.setOnClickListener(view ->
                {
                    try {
                        User user = new User(login.getText().toString(), String.valueOf(password.getText().toString().hashCode()), false, false);
                        userViewModel.insert(user);
                    }
                    catch (SQLiteConstraintException ex)
                    {
                        Toast.makeText(this, "Такой пользователь уже существует", Toast.LENGTH_LONG).show();
                    }
                });
    }
}