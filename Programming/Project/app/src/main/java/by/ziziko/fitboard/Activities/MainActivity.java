package by.ziziko.fitboard.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.PostViewModel;
import by.ziziko.fitboard.ViewModels.UserViewModel;
import by.ziziko.fitboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private PostViewModel postViewModel;
    private UserViewModel userViewModel;
    private String login;
    private List<by.ziziko.fitboard.Entities.User> Users;
    private by.ziziko.fitboard.Entities.User UserL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras()!=null)
        {
            login = getIntent().getExtras().getString("login");
        }
        else
            login = "admin";

        postViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(PostViewModel.class);
        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);


        postViewModel.deleteOldPosts();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        Users = userViewModel.getAllUsers().observe(this, users ->
        {
            Users = users;
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        for (User u: Users)
        {
            if (u.getLogin().equals(login)) {
                UserL = u;
            }
        }

        if (!UserL.isAdmin())
        drawer.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
