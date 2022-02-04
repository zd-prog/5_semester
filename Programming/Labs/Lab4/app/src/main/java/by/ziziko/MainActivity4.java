package by.ziziko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import by.ziziko.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {

    private ArrayAdapter<Book> adapter;

    ActivityMain4Binding binding;
    private List<Book> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookList = JSONHelper.importFromJSON(this);
        adapter = new ArrayAdapter<>(this, R.layout.list_item, bookList);

        ListAdapter listAdapter = new ListAdapter(this, (ArrayList<Book>) bookList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                Book book = (Book) bookList.toArray()[position];
                intent.putExtra("name", book.name);
                intent.putExtra("author", book.author);
                intent.putExtra("data", book.data);
                intent.putExtra("date", book.date);
                intent.putExtra("genre", book.genre);
                startActivity(intent);
            }
        });

    }

}