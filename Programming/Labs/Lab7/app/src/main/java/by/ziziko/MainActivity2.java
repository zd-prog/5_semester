package by.ziziko;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import by.ziziko.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null)
        {
            String name = intent.getStringExtra("name");
            String category = intent.getStringExtra("category");
            String time = intent.getStringExtra("time");
            String ingredients = intent.getStringExtra("ingredients");
            String recipe = intent.getStringExtra("recipe");


            binding.nameRecipe.setText(name);
            binding.categoryRecipe.setText(category);
            binding.timeRecipe.setText(time);
            binding.ingredientsRecipe.setText(ingredients);
            binding.recipeRecipe.setText(recipe);
        }
    }
}