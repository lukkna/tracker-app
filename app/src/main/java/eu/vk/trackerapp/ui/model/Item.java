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
    @Ignore
    public ZonedDateTime dateTime;

    public Item(long uid, String date, String time, int priority, String title, boolean everyDay, boolean everyWeek) {
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
    public Item(String date, String time, int priority, String title, boolean everyDay, boolean everyWeek) {
        this(System.currentTimeMillis(), date, time, priority, title, everyDay, everyWeek);
    }

    @Ignore
    public Item(ZonedDateTime dateTime, int priority, String title, boolean everyDay, boolean everyWeek) {
        this.uid = System.currentTimeMillis();
        this.dateTime = dateTime;
        this.priority = priority;
        this.title = title;
        this.everyDay = everyDay;
        this.everyWeek = everyWeek;
    }

    @Ignore
    public Item() {
    }
}
