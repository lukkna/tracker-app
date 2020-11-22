package eu.vk.trackerapp;

import android.app.Application;

import androidx.room.Room;

import eu.vk.trackerapp.ui.storage.AppDatabase;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

public class TrackerApp extends Application {
    @Override
    public void onCreate() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        DatabaseProvider.initDb(db);

        super.onCreate();
    }
}
