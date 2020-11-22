package eu.vk.trackerapp.ui.model;

import java.time.ZonedDateTime;

public class Item {
    private final ZonedDateTime dateTime;
    private final int priority;
    private final String name;

    public Item(ZonedDateTime dateTime, int priority, String name) {
        this.dateTime = dateTime;
        this.priority = priority;
        this.name = name;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }
}
