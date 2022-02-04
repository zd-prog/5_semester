package by.ziziko.fitboard.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.ziziko.fitboard.Activities.NewCategoryActivity;
import by.ziziko.fitboard.R;
import by.ziziko.fitboard.ViewModels.CategoryViewModel;
import by.ziziko.fitboard.ViewModels.PostViewModel;
import by.ziziko.fitboard.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryViewModel categoryViewModel;
    private LiveData<List<String>> Categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        categoryViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(CategoryViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = root.findViewById(R.id.recyclerview);

        Categories = categoryViewModel.getAllCategories();
        ListAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.category_item, Categories.getValue());

        listView.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewCategoryActivity.class);
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