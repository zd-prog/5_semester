package by.ziziko.fitboard.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.Repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<User>> allUsers;
    public UserViewModel(Application application)
    {
        super(application);
        mRepository = new UserRepository(application);
        allUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers()
    {
        return mRepository.getAllUsers();
    }

    public void insert(User user)
    {
        mRepository.insert(user);
    }

    public LiveData<List<User>> getUserByLoginAndPassword(String login, String password) {return mRepository.getUserByLoginAndPassword(login, password);}

    public LiveData<List<User>> getUserByLogin(String login) {return mRepository.getUserByLogin(login);}
}
