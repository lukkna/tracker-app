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

    @Query("SELECT * FROM item WHERE date = :date OR (period = :period AND date < :date) ORDER BY startTime ASC")
    List<Item> queryByDate(String date, int period);

    @Delete
    void delete(Item... items);

    @Update
    void update(Item... items);
}

