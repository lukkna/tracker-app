package eu.vk.trackerapp.ui;

import java.time.LocalDate;

public class CurrentDateHolder {
    public static String CURRENT_DATE = LocalDate.now().toString();

    private CurrentDateHolder() {
    }
}
