package eu.vk.trackerapp.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.ZonedDateTime;

@Entity
public class Item {
    @PrimaryKey
    public long uid;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "priority")
    public int priority;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "everyDay")
    public boolean everyDay;
    @ColumnInfo(name = "everyWeek")
    public boolean everyWeek;
    @ColumnInfo(name = "completed")
    public boolean completed;
    @Ignore
    public ZonedDateTime dateTime;

    public Item(long uid, String date, String time, int priority, String title, boolean everyDay, boolean everyWeek, boolean completed) {
        this.uid = uid;
        this.dateTime = ZonedDateTime.parse(String.format("%sT%sZ", date, time));
        this.priority = priority;
        this.title = title;
        this.everyDay = everyDay;
        this.everyWeek = everyWeek;
        this.date = date;
        this.time = time;
    }

    @Ignore
    public Item(String date, String time, int priority, String title, boolean everyDay, boolean everyWeek, boolean completed) {
        this(System.currentTimeMillis(), date, time, priority, title, everyDay, everyWeek, completed);
    }

    @Ignore
    public Item(ZonedDateTime dateTime, int priority, String title, boolean everyDay, boolean everyWeek, boolean completed) {
        this.uid = System.currentTimeMillis();
        this.dateTime = dateTime;
        this.priority = priority;
        this.title = title;
        this.everyDay = everyDay;
        this.everyWeek = everyWeek;
        this.completed = completed;
    }

    @Ignore
    public Item() {
    }
}
