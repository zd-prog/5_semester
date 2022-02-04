package by.ziziko;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    interface OnStateClickListener{
        void onStateClick(Recipe recipe, long id);
    }

    private final OnStateClickListener onClickListener;

    private final LayoutInflater inflater;
    private CursorAdapter mCursorAdapter;

    public  ListAdapter(Context context, Cursor c, OnStateClickListener onClickListener, String[] headers)
    {
        this.mCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item,
                c, headers, new int[]{R.id.recipeName, R.id.recipeCategory, R.id.recipeTime}, 0);
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, long id) {
        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.name);
        holder.recipeCategory.setText(recipe.category);
        holder.recipeTime.setText(recipe.time);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(recipe, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeName, recipeCategory, recipeTime;
        ViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.recipeName);
            recipeCategory = view.findViewById(R.id.recipeCategory);
            recipeTime = view.findViewById(R.id.recipeTime);
        }
    }
}
