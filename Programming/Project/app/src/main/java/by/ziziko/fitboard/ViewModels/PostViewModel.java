package by.ziziko.fitboard.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Repositories.PostRepository;

public class PostViewModel extends AndroidViewModel {

    private PostRepository mRepository;

    private final LiveData<List<Post>> allPosts;
    public PostViewModel(Application application)
    {
        super(application);
        mRepository = new PostRepository(application);
        allPosts = mRepository.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts()
    {
        return allPosts;
    }

    public void insert(Post post)
    {
        mRepository.insert(post);
    }

    public void deletePost(Post post) {mRepository.deletePost(post);}

    public void deleteOldPosts(){mRepository.deleteOldPosts();}

}
