package by.ziziko.fitboard.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.ziziko.fitboard.Entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Query("Delete from User")
    void deleteAll();

    @Query("Select * from User")
    LiveData<List<User>> getUsers();

    @Query("Select * from User where login like :login and password = :password")
    LiveData<List<User>> getUserByLoginAndPassword(String login, String password);

    @Query("Select * from User where login like :login")
    LiveData<List<User>> getUserByLogin(String login);
}
