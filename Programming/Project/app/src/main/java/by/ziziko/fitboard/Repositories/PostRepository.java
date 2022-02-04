package by.ziziko.fitboard.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import by.ziziko.fitboard.Dao.PostDao;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Room.FITRoom;

public class PostRepository {
    private PostDao mPostDao;
    private LiveData<List<Post>> allPosts;

    public PostRepository(Application application)
    {
        FITRoom db = FITRoom.getDatabase(application);
        mPostDao = db.postDao();
        allPosts = mPostDao.getPosts();
    }

    public LiveData<List<Post>> getAllPosts()
    {
        return allPosts;
    }

    public void insert(Post post)
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            mPostDao.insert(post);
        });
    }

    public void deletePost(Post post)
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            mPostDao.deletePost(post);
        });
    }

    public void deleteOldPosts()
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            mPostDao.deleteOldPosts(new Date());
        });
    }

}
