package by.ziziko.fitboard.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.ziziko.fitboard.Dao.CategoryDao;
import by.ziziko.fitboard.Dao.PostDao;
import by.ziziko.fitboard.Dao.UserDao;
import by.ziziko.fitboard.Entities.Category;
import by.ziziko.fitboard.Entities.Post;
import by.ziziko.fitboard.Entities.User;

@Database(entities = {User.class, Post.class, Category.class}, version = 1, exportSchema = false)
public abstract class FITRoom extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract CategoryDao categoryDao();

    private static volatile FITRoom INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FITRoom getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FITRoom.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FITRoom.class, "fit_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();

                User user = new User("admin", String.valueOf("admin".hashCode()), true, false);
                dao.insert(user);
                user = new User("zizikod", String.valueOf("zizikod".hashCode()), false, false);
                dao.insert(user);
                user = new User("blackList", String.valueOf("blackList".hashCode()), false, true);
                dao.insert(user);
            });
        }
    };
}
