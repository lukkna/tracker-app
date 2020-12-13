package eu.vk.trackerapp.ui.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.vk.trackerapp.ui.model.Workout;

@Dao
public interface WorkoutDao {
    @Insert
    void insertAll(Workout... items);

    @Query("SELECT * FROM workout ORDER BY date DESC")
    List<Workout> queryAll();

    @Delete
    void delete(Workout... items);

    @Update
    void update(Workout... items);
}
