package by.ziziko.fitboard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private by.ziziko.fitboard.Entities.User UserLP;
    private List<User> Users;
    private User UserL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users ->
        {
            Users = users;
        });

        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        TextView loginButton = findViewById(R.id.loginButton);
        TextView registration = findViewById(R.id.registration);

        loginButton.setOnClickListener(view ->
        {
            for (User u: Users)
            {
                if (u.getLogin().equals(login.getText().toString()) && u.getPassword().equals(String.valueOf(password.getText().toString().hashCode())))
                {
                    UserLP = u;

                }
                else {
                    if (u.getLogin().equals(login.getText().toString())) {
                        UserL = u;
                    }
                }
            }

            if(UserLP != null)
            {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("login", UserLP.getLogin());
                startActivity(intent);
            }
            else {
                if (UserL != null)
                    Toast.makeText(this, "Проверьте корректность пароля!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_LONG).show();

            }
        });



        registration.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

}