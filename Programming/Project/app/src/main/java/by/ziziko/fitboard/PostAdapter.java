package by.ziziko.fitboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.ziziko.fitboard.Entities.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final LiveData<List<Post>> posts;

    public PostAdapter(Context context, LiveData<List<Post>> posts)
    {
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = posts.getValue().get(position);
        holder.postText.setText(post.getText());
        holder.postTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView postTitle, postText;
        ViewHolder(View view)
        {
            super(view);

            postTitle = view.findViewById(R.id.post_title);
            postText = view.findViewById(R.id.post_text);
        }
    }
}
