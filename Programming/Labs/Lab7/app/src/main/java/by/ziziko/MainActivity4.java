package by.ziziko;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private List<Recipe> recipeList;
    private ListAdapter listAdapter;
    SQLiteDatabase db;
    Cursor cursor;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        helper = new DatabaseHelper(getApplicationContext());

        db = helper.getReadableDatabase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        EditText name = findViewById(R.id.RecipeName);
        EditText category = findViewById(R.id.RecipeCategory);
        EditText time = findViewById(R.id.TimeRecipe);
        EditText ingredients = findViewById(R.id.Ingredients);
        EditText recipe = findViewById(R.id.RecipeRecipe);
        Button button = findViewById(R.id.Button);

        name.setText(bundle.getString("name"));
        category.setText(bundle.getString("category"));
        time.setText(bundle.getString("time"));
        ingredients.setText(bundle.getString("ingredients"));
        recipe.setText(bundle.getString("recipe"));



        listAdapter = new ListAdapter(this, (ArrayList<Recipe>) recipeList);

        String selection = DBContract.DBEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(bundle.getLong("id"))};

        cursor = db.query(
                DBContract.DBEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Recipe Recipe = new Recipe(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        String selectionDel = DBContract.DBEntry._ID + " LIKE ?";
        String[] selectionArgsDel = {String.valueOf(bundle.getLong("id"))};
        db.delete(DBContract.DBEntry.TABLE_NAME, selectionDel, selectionArgsDel);
        RenewCursor(selection, selectionArgs);
        listAdapter.notifyDataSetChanged();

        button.setOnClickListener(v ->
        {
            Recipe rc = new Recipe(name.getText().toString(), category.getText().toString(),
                    ingredients.getText().toString(), recipe.getText().toString(), time.getText().toString());
            ContentValues values = new ContentValues();
            values.put(DBContract.DBEntry.COLUMN_NAME_NAME, Recipe.name);
            values.put(DBContract.DBEntry.COLUMN_NAME_CATEGORY, Recipe.category);
            values.put(DBContract.DBEntry.COLUMN_NAME_INGREDIENTS, Recipe.ingredients);
            values.put(DBContract.DBEntry.COLUMN_NAME_TIME, Recipe.time);
            values.put(DBContract.DBEntry.COLUMN_NAME_RECIPE, Recipe.recipe);

            long newRowId;
            newRowId = db.insert(DBContract.DBEntry.TABLE_NAME, null, values);

            Intent intentPrev = new Intent(MainActivity4.this, MainActivity.class);
            startActivity(intentPrev);
        });
    }

    public Cursor RenewCursor(String selection, String[] selectionArgs)
    {
        cursor = db.query(
                DBContract.DBEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }
}