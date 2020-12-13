package eu.vk.trackerapp.ui.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Entity
public class Workout {
    @PrimaryKey
    public long uid;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "exercises")
    public String exercises;

    public Workout(long uid, String date, String name, String exercises) {
        this.uid = uid;
        this.date = date;
        this.exercises = exercises;
        this.name = name;
    }

    @Ignore
    public Workout(String date, String name, List<Exercise> exercises) {
        this(
                System.currentTimeMillis(),
                date,
                name,
                exercises.stream().map(Exercise::toStorageString).collect(joining(">"))
        );
        System.out.println();
    }

    @Ignore
    public Workout(String date, String name) {
        this(System.currentTimeMillis(), date, name, "");
    }

    public List<Exercise> getExercises() {
        return Arrays.stream(exercises.split(">"))
                .map(String::trim)
                .map(Exercise::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises.stream()
                .map(Exercise::toStorageString)
                .map(String::trim)
                .collect(joining(">"));
        System.out.println();
    }
}
