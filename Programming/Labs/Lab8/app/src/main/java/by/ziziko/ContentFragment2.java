package by.ziziko;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import java.util.List;

import by.ziziko.databinding.FragmentContent2Binding;
import by.ziziko.databinding.FragmentContentBinding;

interface OnSelectedRecipeListener
{
    void onRecipeSelected(int position);
}


public class ContentFragment2 extends Fragment {

    FragmentContent2Binding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void render(LayoutInflater inflater) {
        binding =  FragmentContent2Binding.inflate(inflater);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContent2Binding.inflate(inflater);

        Intent intent = getActivity().getIntent();
        if (intent != null)
        {
            String name = intent.getStringExtra("name");
            String category = intent.getStringExtra("category");
            String time = intent.getStringExtra("time");
            String ingredients = intent.getStringExtra("ingredients");
            String recipe = intent.getStringExtra("recipe");

            binding.nameRecipe.setText(name);
            binding.categoryRecipe.setText(category);
            binding.timeRecipe.setText(time);
            binding.ingredientsRecipe.setText(ingredients);
            binding.recipeRecipe.setText(recipe);
        }
        return binding.getRoot();
    }

    public void setSelectedItemV(Intent intent)
    {



    }


}
