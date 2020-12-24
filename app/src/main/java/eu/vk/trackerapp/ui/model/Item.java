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
    @ColumnInfo(name = "period")
    public int period; // 0 - do not repeat, 1 - work days, 2 - weekends
    @ColumnInfo(name = "weekDay")
    public int weekDay;
    @Ignore
    public ZonedDateTime startDateTime;
    @Ignore
    public ZonedDateTime endDateTime;

    public Item(String date, String startTime, String endTime, int priority, String title, int weekDay, int period) {
        this.uid = System.currentTimeMillis();
        this.weekDay = weekDay;
        this.startDateTime = ZonedDateTime.parse(String.format("%sT%sZ", date, startTime));
        this.endDateTime = ZonedDateTime.parse(String.format("%sT%sZ", date, endTime));
        this.priority = priority;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
    }

    @Ignore
    public Item() {
    }
}
