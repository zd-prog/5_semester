package by.ziziko.fitboard.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import by.ziziko.fitboard.Converters.DateConverter;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Entities.User;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Post post);

    @Query("Delete from Post")
    void deleteAll();

    @Query("Select * from Post")
    LiveData<List<Post>> getPosts();

    @Delete
    void deletePost(Post Post);

    @TypeConverters(DateConverter.class)
    @Query("Delete from Post where (julianday(date) - julianday(:today)) > 30 ")
    void deleteOldPosts(Date today);

    @Query("Select category from Post")
    LiveData<List<String>> getCategories();
}
