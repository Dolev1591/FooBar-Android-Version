package DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import objects.User;
// Data access object for the user entity
@Dao
public interface UserDao {
    //insert, get and delete a user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM user LIMIT 1")
    User getCurrentUser();

    @Query("DELETE FROM user")
    void deleteUser();
    @Transaction // This ensures all operations are performed in a single transaction
    default void updateToken(String newToken) {
        deleteUser();
        User user=new User();
        insertUser(user);
    }
}