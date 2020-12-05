package eu.vk.trackerapp.ui;

import java.time.LocalDate;

public class CurrentDateHolder {
    public static String CURRENT_DATE_STRING = LocalDate.now().toString();
    public static LocalDate CURRENT_DATE = LocalDate.parse(CURRENT_DATE_STRING);

    private CurrentDateHolder() {
    }
}
