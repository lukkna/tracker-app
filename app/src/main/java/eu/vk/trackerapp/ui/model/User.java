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

    @Ignore
    public User(String name, int age, double weight, boolean male) {
        this(System.currentTimeMillis(), name, age, weight, male);
    }

    public User(long uid, String name, int age, double weight, boolean male) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.male = male;
    }
}
