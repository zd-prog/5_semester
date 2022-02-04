package by.ziziko;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    interface OnRecipeClickListener
    {
        void onRecipeClick(Recipe recipe, int position);
    }

    private final OnRecipeClickListener onRecipeClickListener;
    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.onRecipeClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTime.setText(recipe.time);
        holder.recipeName.setText(recipe.name);
        holder.recipeCategory.setText(recipe.category);

        holder.itemView.setOnClickListener(v ->
        {
            onRecipeClickListener.onRecipeClick(recipe, position);
        });

        holder.itemView.setOnLongClickListener(view ->
        {
            setPosition(holder.getPosition());
            return  false;
        });
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        final TextView recipeName, recipeCategory, recipeTime;
        ViewHolder(View view)
        {
            super(view);
            recipeName = view.findViewById(R.id.recipeName);
            recipeCategory = view.findViewById(R.id.recipeCategory);
            recipeTime = view.findViewById(R.id.recipeTime);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 0, 0, "Удалить");
            contextMenu.add(0, 1, 0, "Изменить");
            contextMenu.add(0, 2, 0, "Просмотреть");

        }
    }



}

