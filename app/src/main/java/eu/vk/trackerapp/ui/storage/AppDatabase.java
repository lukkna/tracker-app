package eu.vk.trackerapp.ui.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import eu.vk.trackerapp.ui.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
