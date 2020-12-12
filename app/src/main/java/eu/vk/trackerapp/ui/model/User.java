package eu.vk.trackerapp.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public long uid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "age")
    public int age;
    @ColumnInfo(name = "weight")
    public double weight;
    @ColumnInfo(name = "male")
    public boolean male;
    @ColumnInfo(name = "height")
    public int height;

    @Ignore
    public User(String name, int age, double weight, boolean male, int height) {
        this(System.currentTimeMillis(), name, age, weight, male, height);
    }

    public User(long uid, String name, int age, double weight, boolean male, int height) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.male = male;
        this.height = height;
    }
}
