package by.ziziko.fitboard.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.ziziko.fitboard.Activities.NewPostActivity;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.PostAdapter;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.PostViewModel;
import by.ziziko.fitboard.ViewModels.UserViewModel;
import by.ziziko.fitboard.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private PostViewModel postViewModel;
    private UserViewModel userViewModel;

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;


    View view;
    private LiveData<List<Post>> Posts;
    private String login;
    private by.ziziko.fitboard.Entities.User User;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity().getParentActivityIntent().getExtras()!=null)
        {
            login = getActivity().getParentActivityIntent().getExtras().getString("login");
        }
        else
            login = "admin";

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        postViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(PostViewModel.class);
        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);

        Posts = postViewModel.getAllPosts();
        view = root;
        recyclerView = root.findViewById(R.id.posts);
        PostAdapter adapter = new PostAdapter(getActivity().getApplicationContext(), Posts);
        recyclerView.setAdapter(adapter);

        userViewModel.getUserByLogin(login).observe(getActivity(), users ->
        {
            User = users.get(0);
        });
        if(User.isInBlacklist())
            binding.fab.setVisibility(View.INVISIBLE);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewPostActivity.class);
                intent.putExtra("login", getActivity().getIntent().getExtras().getString("login"));
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}