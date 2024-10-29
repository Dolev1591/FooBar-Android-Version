package DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import DAOs.PostDao;
import DAOs.UserDao;
import convertors.StringConverter;
import entities.Post;
import objects.User;
// Database class for the app
@Database(entities = { User.class, Post.class}, version = 12,exportSchema = false)
@TypeConverters({StringConverter.class})
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract UserDao UserDao();

    private static volatile AppDB INSTANCE;
    //making sure only one instance of the database is created for each user
    public static AppDB getDatabase(final Context context, final String userId) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDB.class, "app_database_" + userId)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
