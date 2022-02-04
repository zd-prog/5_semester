package by.ziziko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Recipe> adapter;
    private List<Recipe> recipes;
    ListView listView;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.drawerList);

        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.nav_draw, mScreenTitles));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,null,
                R.string.drawer_open,
                R.string.drawer_close
        );

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    public void onDrawerOpened(View drawerView) {
        getSupportActionBar().setTitle(mDrawerTitle);
        supportInvalidateOptionsMenu();
    }

    public void onDrawerClosed(View view) {
        getSupportActionBar().setTitle(mTitle);
        supportInvalidateOptionsMenu();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_new :
                addContact();
                return true;
            case R.id.sort:
                Collections.sort(recipes);
                adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


    private void addContact() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_new).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    Navigation Drawer;
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mScreenTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}