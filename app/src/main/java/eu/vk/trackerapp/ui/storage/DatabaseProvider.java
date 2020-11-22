package eu.vk.trackerapp.ui.storage;

public class DatabaseProvider {
    private static AppDatabase db;

    public static AppDatabase getInstance() {
        return db;
    }

    public static void initDb(AppDatabase db) {
        DatabaseProvider.db = db;
    }
}
