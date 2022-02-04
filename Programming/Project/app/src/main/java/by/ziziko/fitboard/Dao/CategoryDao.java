package by.ziziko.fitboard.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.ziziko.fitboard.Entities.Category;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Category category);

    @Query("Delete from Category")
    void deleteAll();

    @Query("Select category from Category")
    LiveData<List<String>> getCategories();

    @Delete
    void deleteCategory(Category category);

}
