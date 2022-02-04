package by.ziziko;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import by.ziziko.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private List<Recipe> recipeList;
    ListAdapter listAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_new :
                addContact();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void addContact() {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (JSONHelper.importFromJSON(this) == null)
            recipeList = new ArrayList<>();
        else
            recipeList = JSONHelper.importFromJSON(this);

        listAdapter = new ListAdapter(this, (ArrayList<Recipe>) recipeList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {

            ListView listview = findViewById(R.id.listview);
            registerForContextMenu(listview);
            ShowRecipe(position);
        });
    }

    private void ShowRecipe(int position) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        Recipe recipe = (Recipe) recipeList.toArray()[position];
        intent.putExtra("name", recipe.name);
        intent.putExtra("category", recipe.category);
        intent.putExtra("time", recipe.time);
        intent.putExtra("ingredients", recipe.ingredients);
        intent.putExtra("recipe", recipe.recipe);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "Удалить");
        menu.add(0, 1, 0, "Изменить");
        menu.add(0, 2, 0, "Просмотреть");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case 0:
                Dialog dialogDel = new Dialog(MainActivity.this);
                dialogDel.setContentView(R.layout.dialog);
                TextView textViewDel = dialogDel.findViewById(R.id.dialog_info);
                textViewDel.setText("Вы хотите удалить этот рецепт?");
                Button yes = dialogDel.findViewById(R.id.dialog_button_yes);
                Button no = dialogDel.findViewById(R.id.dialog_button_no);
                dialogDel.show();
                yes.setOnClickListener(v->
                {
                    DeleteRecipe(info.position);
                    dialogDel.cancel();
                });
                no.setOnClickListener(v -> dialogDel.cancel());
                break;
            case 1:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                TextView textView = dialog.findViewById(R.id.dialog_info);
                textView.setText("Вы хотите изменить этот рецепт?");
                Button Yes = dialog.findViewById(R.id.dialog_button_yes);
                Button No = dialog.findViewById(R.id.dialog_button_no);
                dialog.show();
                Yes.setOnClickListener(v->
                {
                    ChangeRecipe(info.position);
                    dialog.cancel();
                });
                No.setOnClickListener(v -> dialog.cancel());

                break;
            case 2:
                ShowRecipe(info.position);
                break;
    }
        return super.onContextItemSelected(item);
    }

    private void DeleteRecipe(int position) {
        Recipe recipe = (Recipe) recipeList.toArray()[position];
        recipeList.remove(recipe);
        JSONHelper.exportToJSON(this, recipeList);
        listAdapter.notifyDataSetChanged();
    }

    private void ChangeRecipe(int position) {
        Intent intent = new Intent(MainActivity.this, MainActivity4.class);
        Recipe recipe = (Recipe) recipeList.toArray()[position];
        intent.putExtra("name", recipe.name);
        intent.putExtra("category", recipe.category);
        intent.putExtra("time", recipe.time);
        intent.putExtra("ingredients", recipe.ingredients);
        intent.putExtra("recipe", recipe.recipe);
        intent.putExtra("position", position);

        startActivity(intent);
    }
}