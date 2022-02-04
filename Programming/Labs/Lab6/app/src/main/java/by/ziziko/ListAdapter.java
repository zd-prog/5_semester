package by.ziziko;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {
    public ListAdapter(Context context, ArrayList<Recipe> recipes)
    {
        super(context, R.layout.list_item, recipes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Recipe recipe = (Recipe) getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from((getContext())).inflate(R.layout.list_item, parent, false);
        }

        TextView recipeName = convertView.findViewById(R.id.recipeName);
        TextView recipeCategory = convertView.findViewById(R.id.recipeCategory);
        TextView recipeTime = convertView.findViewById(R.id.recipeTime);

        recipeName.setText(recipe.name);
        recipeCategory.setText(recipe.category);
        recipeTime.setText(recipe.time);

        return convertView;
    }}
