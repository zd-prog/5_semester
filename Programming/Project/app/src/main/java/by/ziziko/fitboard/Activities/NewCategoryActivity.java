package by.ziziko.fitboard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.CategoryViewModel;
import by.ziziko.fitboard.ViewModels.PostViewModel;

public class NewCategoryActivity extends AppCompatActivity {

    private CategoryViewModel categoryViewModel;
    private LiveData<List<String>> Categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        categoryViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(CategoryViewModel.class);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {

            Categories = categoryViewModel.getAllCategories();

            EditText newCategory = findViewById(R.id.edit_word);
            if (!newCategory.getText().toString().equals(""))
            {
                Categories.getValue().add(newCategory.getText().toString());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}