package by.ziziko;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private List<Recipe> recipeList;
    DatabaseHelper mDbHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mDbHelper = new DatabaseHelper(this);

        Button button = findViewById(R.id.button);
        EditText text = findViewById(R.id.editText);
        EditText category = findViewById(R.id.editText2);
        EditText ingredients = findViewById(R.id.ingredients);
        EditText time = findViewById(R.id.Time);
        EditText recipe = findViewById(R.id.recipe);


        button.setOnClickListener(v ->
        {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            if (!text.getText().toString().equals("") || !category.getText().toString().equals("") ||
                    !ingredients.getText().toString().equals("") || !time.getText().toString().equals("") ||
                    !recipe.getText().toString().equals(""))
           {

               Recipe Recipe = new Recipe(text.getText().toString(), category.getText().toString(), ingredients.getText().toString(), recipe.getText().toString(), time.getText().toString());

               ContentValues values = new ContentValues();
               values.put(DBContract.DBEntry.COLUMN_NAME_NAME, Recipe.name);
               values.put(DBContract.DBEntry.COLUMN_NAME_CATEGORY, Recipe.category);
               values.put(DBContract.DBEntry.COLUMN_NAME_INGREDIENTS, Recipe.ingredients);
               values.put(DBContract.DBEntry.COLUMN_NAME_TIME, Recipe.time);
               values.put(DBContract.DBEntry.COLUMN_NAME_RECIPE, Recipe.recipe);

               long newRowId;
               newRowId = db.insert(DBContract.DBEntry.TABLE_NAME, null, values);

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