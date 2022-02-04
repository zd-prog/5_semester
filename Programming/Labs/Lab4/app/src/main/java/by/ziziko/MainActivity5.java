package by.ziziko;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import by.ziziko.databinding.ActivityMain5Binding;

public class MainActivity5 extends AppCompatActivity {

    ActivityMain5Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null)
        {
            String name = intent.getStringExtra("name");
            String author = intent.getStringExtra("author");
            String data = intent.getStringExtra("data");
            String date = intent.getStringExtra("date");
            String genre = intent.getStringExtra("genre");

            binding.name3.setText(name);
            binding.author3.setText(author);
            binding.data3.setText(data);
            binding.date3.setText(date);
            binding.genre3.setText(genre);
        }
    }
}