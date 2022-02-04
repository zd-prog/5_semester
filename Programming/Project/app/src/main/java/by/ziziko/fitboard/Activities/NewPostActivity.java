package by.ziziko.fitboard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.CategoryViewModel;
import by.ziziko.fitboard.ViewModels.PostViewModel;

public class NewPostActivity extends AppCompatActivity {

    private CategoryViewModel categoryViewModel;
    private PostViewModel postViewModel;

    private LiveData<List<String>> Categories;
    private LiveData<List<Post>> Posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        categoryViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(CategoryViewModel.class);
        postViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(PostViewModel.class);

        Categories = categoryViewModel.getAllCategories();
        Posts = postViewModel.getAllPosts();

        Spinner spinner = findViewById(R.id.post_category);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_item, Categories.getValue());
        spinner.setAdapter(adapter);

        Button save = findViewById(R.id.button_save_post);
        EditText title = findViewById(R.id.new_post_title);
        EditText text = findViewById(R.id.post_fulltext);

        save.setOnClickListener(view ->
        {
            if (!title.getText().toString().equals("") || !text.getText().toString().equals(""))
            {
                String selected = spinner.getSelectedItem().toString();
                Post post = new Post();
                post.setTitle(title.getText().toString());
                post.setCategory(selected);
                post.setDate(new Date());
                post.setText(text.getText().toString());

                Posts.getValue().add(post);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("login", getIntent().getExtras().getString("login"));
                startActivity(intent);
            }
        });
    }
}