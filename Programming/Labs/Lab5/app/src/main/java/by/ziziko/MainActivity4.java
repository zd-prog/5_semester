package by.ziziko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private List<Recipe> recipeList;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

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


        if (JSONHelper.importFromJSON(this) == null)
            recipeList = new ArrayList<>();
        else
            recipeList = JSONHelper.importFromJSON(this);

        listAdapter = new ListAdapter(this, (ArrayList<Recipe>) recipeList);

        Recipe Recipe = (by.ziziko.Recipe) recipeList.toArray()[bundle.getInt("position")];
        recipeList.remove(Recipe);
        listAdapter.notifyDataSetChanged();

        button.setOnClickListener(v ->
        {
            Recipe.name = name.getText().toString();
            Recipe.category = category.getText().toString();
            Recipe.time = time.getText().toString();
            Recipe.ingredients = ingredients.getText().toString();
            Recipe.recipe = recipe.getText().toString();
            recipeList.add(Recipe);
            JSONHelper.exportToJSON(this, recipeList);
            listAdapter.notifyDataSetChanged();
            Intent intentPrev = new Intent(MainActivity4.this, MainActivity.class);
            startActivity(intentPrev);
        });
    }
}