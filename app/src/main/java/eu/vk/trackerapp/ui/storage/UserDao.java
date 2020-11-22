package eu.vk.trackerapp.ui.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import eu.vk.trackerapp.ui.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertAll(User... users);

    @Query("SELECT * FROM user LIMIT 1")
    User retrieveUser();
}
