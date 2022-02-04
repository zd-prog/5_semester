package by.ziziko.fitboard.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.ziziko.fitboard.Entities.Category;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Repositories.CategoryRepository;
import by.ziziko.fitboard.Repositories.PostRepository;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;

    private final LiveData<List<String>> allCategories;
    public CategoryViewModel(Application application)
    {
        super(application);
        mRepository = new CategoryRepository(application);
        allCategories = mRepository.getAllCategories();
    }

    public LiveData<List<String>> getAllCategories()
    {
        return allCategories;
    }

    public void insert(String category)
    {
        mRepository.insert(category);
    }

    public void deletePost(Category category) {mRepository.deleteCategory(category);}

}
