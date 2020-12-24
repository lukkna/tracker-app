package eu.vk.trackerapp.ui.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.model.User;
import eu.vk.trackerapp.ui.model.Workout;

@Database(entities = {User.class, Item.class, Workout.class}, version = 12)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ItemDao itemDao();

    public abstract WorkoutDao workoutDao();
}
