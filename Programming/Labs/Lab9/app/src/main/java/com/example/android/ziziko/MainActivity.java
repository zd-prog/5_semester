package com.example.android.ziziko;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private CardViewModel mCardViewModel;
    CardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new CardListAdapter(new CardListAdapter.CardDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCardViewModel = new ViewModelProvider(this).get(CardViewModel.class);


        mCardViewModel.getAllCards().observe(this, cards -> {
            adapter.submitList(cards);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewCardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()) {


            case "Delete":
                mCardViewModel.delete(mCardViewModel.getAllCards().getValue().get(adapter.getPosition()));
                break;
        }
        return super.onContextItemSelected(item);    }
}
