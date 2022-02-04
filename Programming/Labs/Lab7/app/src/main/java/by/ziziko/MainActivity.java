package by.ziziko;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import by.ziziko.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter recipeAdapter;
    ArrayList<Recipe> recipes;

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

        RecyclerView recyclerView = findViewById(R.id.listview);
        ListAdapter.OnStateClickListener clickListener = new ListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Recipe recipe, long id) {
                ListView listview = findViewById(R.id.listview);
                registerForContextMenu(listview);
                ShowRecipe(id);
            }
        };
        ListAdapter adapter = new ListAdapter(this, recipes, clickListener);
        recyclerView.setAdapter(adapter);


        helper = new DatabaseHelper(getApplicationContext());

        Task task = new Task();
        task.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        cursor = db.query(
                DBContract.DBEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        String[] headers = new String[]{DBContract.DBEntry.COLUMN_NAME_NAME,
                DBContract.DBEntry.COLUMN_NAME_CATEGORY, DBContract.DBEntry.COLUMN_NAME_TIME};
        recipeAdapter = new SimpleCursorAdapter(this, R.layout.list_item,
                cursor, headers, new int[]{R.id.recipeName, R.id.recipeCategory, R.id.recipeTime}, 0);
        binding.listview.setAdapter(recipeAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) ->
        {
            ListView listview = findViewById(R.id.listview);
            registerForContextMenu(listview);
            ShowRecipe(id);
        });

    }

    private void ShowRecipe(long id) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        String selection = DBContract.DBEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

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
        Recipe recipe = new Recipe(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
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
                    DeleteRecipe(info.id);
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
                    ChangeRecipe(info.id);
                    dialog.cancel();
                });
                No.setOnClickListener(v -> dialog.cancel());

                break;
            case 2:
                ShowRecipe(info.id);
                break;
    }
        return super.onContextItemSelected(item);
    }

    private void DeleteRecipe(long id) {
        String selectionDel = DBContract.DBEntry._ID + " LIKE ?";
        String[] selectionArgsDel = {String.valueOf(id)};
        db.delete(DBContract.DBEntry.TABLE_NAME, selectionDel, selectionArgsDel);
        RenewCursor(selectionDel, selectionArgsDel);
        recipeAdapter.notifyDataSetChanged();
    }

    private void ChangeRecipe(long id) {
        Intent intent = new Intent(MainActivity.this, MainActivity4.class);
        String selection = DBContract.DBEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

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
        Recipe recipe = new Recipe(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        intent.putExtra("name", recipe.name);
        intent.putExtra("category", recipe.category);
        intent.putExtra("time", recipe.time);
        intent.putExtra("ingredients", recipe.ingredients);
        intent.putExtra("recipe", recipe.recipe);
        intent.putExtra("id", id);

        startActivity(intent);
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

    public class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast toast = Toast.makeText(getApplicationContext(), "Асинхронная задача до выполнения", Toast.LENGTH_LONG);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                TimeUnit.SECONDS.sleep(5);
            }
            catch (InterruptedException ex)
            {
                Log.d(ex.getMessage(), ex.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast toast = Toast.makeText(getApplicationContext(), "Асинхронная задача после выполнения", Toast.LENGTH_LONG);
        }
    }
}