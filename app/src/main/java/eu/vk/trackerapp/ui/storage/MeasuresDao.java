package eu.vk.trackerapp.ui.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.vk.trackerapp.ui.model.Measures;

@Dao
public interface MeasuresDao {
    @Insert
    void insertAll(Measures... items);

    @Query("SELECT * FROM measures ORDER BY date DESC")
    List<Measures> queryAll();

    @Update
    void update(Measures... items);

    @Delete
    void delete(Measures... items);
}
