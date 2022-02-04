package by.ziziko;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private List<Recipe> recipeList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (JSONHelper.importFromJSON(this) == null)
            recipeList = new ArrayList<>();
        else
            recipeList = JSONHelper.importFromJSON(this);

        Button button = findViewById(R.id.button);
        EditText text = findViewById(R.id.editText);
        EditText category = findViewById(R.id.editText2);
        EditText ingredients = findViewById(R.id.ingredients);
        EditText time = findViewById(R.id.Time);
        EditText recipe = findViewById(R.id.recipe);


        button.setOnClickListener(v ->
        {
            if (!text.getText().toString().equals("") || !category.getText().toString().equals("") || !ingredients.getText().toString().equals("") ||
                    !time.getText().toString().equals("") || !recipe.getText().toString().equals(""))
            {

                Recipe Recipe = new Recipe(text.getText().toString(), category.getText().toString(), ingredients.getText().toString(), recipe.getText().toString(), time.getText().toString());
                recipeList.add(Recipe);

                JSONHelper.exportToJSON(this, recipeList);

                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(this, "Проверьте правильность введённых данных", Toast.LENGTH_LONG);
            }
        });
    }
}