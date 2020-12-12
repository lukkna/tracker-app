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
    @ColumnInfo(name = "startTime")
    public String startTime;
    @ColumnInfo(name = "endTime")
    public String endTime;
    @ColumnInfo(name = "priority")
    public int priority;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "everyDay")
    public boolean everyDay;
    @ColumnInfo(name = "everyWeek")
    public boolean everyWeek;
    @ColumnInfo(name = "weekDay")
    public int weekDay;
    @ColumnInfo(name = "completed")
    public boolean completed;
    @Ignore
    public ZonedDateTime startDateTime;
    @Ignore
    public ZonedDateTime endDateTime;

    public Item(long uid, String date, String startTime, String endTime, int priority, String title, boolean everyDay, boolean everyWeek, int weekDay, boolean completed) {
        this.uid = uid;
        this.weekDay = weekDay;
        this.startDateTime = ZonedDateTime.parse(String.format("%sT%sZ", date, startTime));
        this.endDateTime = ZonedDateTime.parse(String.format("%sT%sZ", date, endTime));
        this.priority = priority;
        this.title = title;
        this.everyDay = everyDay;
        this.everyWeek = everyWeek;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completed = completed;
    }

    @Ignore
    public Item(String date, String startTime, String endTime, int priority, String title, boolean everyDay, boolean everyWeek, int weekDay, boolean completed) {
        this(System.currentTimeMillis(), date, startTime, endTime, priority, title, everyDay, everyWeek, weekDay, completed);
    }

    @Ignore
    public Item(ZonedDateTime startDateTime, int priority, String title, boolean everyDay, boolean everyWeek, int weekDay, boolean completed) {
        this.uid = System.currentTimeMillis();
        this.startDateTime = startDateTime;
        this.priority = priority;
        this.title = title;
        this.everyDay = everyDay;
        this.everyWeek = everyWeek;
        this.completed = completed;
        this.weekDay = weekDay;
    }

    @Ignore
    public Item() {
    }
}
