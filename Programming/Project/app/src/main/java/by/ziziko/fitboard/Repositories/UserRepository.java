package by.ziziko.fitboard.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.ziziko.fitboard.Dao.UserDao;
import by.ziziko.fitboard.Entities.User;
import by.ziziko.fitboard.Room.FITRoom;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application)
    {
        FITRoom db = FITRoom.getDatabase(application);
        mUserDao = db.userDao();
        allUsers = mUserDao.getUsers();
    }

    public LiveData<List<User>> getAllUsers()
    {
        return allUsers;
    }

    public void insert(User user)
    {
        FITRoom.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public LiveData<List<User>> getUserByLoginAndPassword(String login, String password)
    {
        return mUserDao.getUserByLoginAndPassword(login, password);
    }

    public LiveData<List<User>> getUserByLogin(String login)
    {
        return mUserDao.getUserByLogin(login);
    }
}
