package eu.vk.trackerapp.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Measures {
    @PrimaryKey
    public long uid;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "weight")
    public Double weight;
    @ColumnInfo(name = "waistNarrowest")
    public Double waistNarrowest;
    @ColumnInfo(name = "waistWidest")
    public Double waistWidest;
    @ColumnInfo(name = "butt")
    public Double butt;
    @ColumnInfo(name = "thigh")
    public Double thigh;
    @ColumnInfo(name = "shin")
    public Double shin;
    @ColumnInfo(name = "chest")
    public Double chest;
    @ColumnInfo(name = "bicep")
    public Double bicep;
    @ColumnInfo(name = "wrist")
    public Double wrist;

    public Measures(String date,
                    Double weight,
                    Double waistNarrowest,
                    Double waistWidest,
                    Double butt,
                    Double thigh,
                    Double shin,
                    Double chest,
                    Double bicep,
                    Double wrist) {
        uid = System.currentTimeMillis();
        this.date = date;
        this.weight = weight;
        this.waistNarrowest = waistNarrowest;
        this.waistWidest = waistWidest;
        this.butt = butt;
        this.thigh = thigh;
        this.shin = shin;
        this.chest = chest;
        this.bicep = bicep;
        this.wrist = wrist;
    }
}
