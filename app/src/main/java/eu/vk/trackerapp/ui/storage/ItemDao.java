package eu.vk.trackerapp.ui.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.vk.trackerapp.ui.model.Item;

@Dao
public interface ItemDao {
    @Insert
    void insertAll(Item... items);

    @Query("SELECT * FROM item WHERE date = :date")
    List<Item> queryByDate(String date);

    @Delete
    void delete(Item... items);

    @Update
    void update(Item... items);
}
//    @Insert
//    void insertAll(User... users);
//
//    @Query("SELECT * FROM user LIMIT 1")

