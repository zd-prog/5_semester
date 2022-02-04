package by.ziziko.fitboard.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.ziziko.fitboard.Dao.CategoryDao;
import by.ziziko.fitboard.Dao.PostDao;
import by.ziziko.fitboard.Entities.Category;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Room.FITRoom;

public class CategoryRepository {

    private CategoryDao mCategoryDao;
    private LiveData<List<String>> allCategories;

    public CategoryRepository(Application application)
    {
        FITRoom db = FITRoom.getDatabase(application);
        mCategoryDao = db.categoryDao();
        allCategories = mCategoryDao.getCategories();
    }

    public LiveData<List<String>> getAllCategories()
    {
        return allCategories;
    }

    public void insert(String category)
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            Category cat = new Category(category);
            mCategoryDao.insert(cat);
        });
    }

    public void deleteCategory(Category category)
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            mCategoryDao.deleteCategory(category);
        });
    }
}
