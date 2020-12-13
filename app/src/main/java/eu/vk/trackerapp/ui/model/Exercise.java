package eu.vk.trackerapp.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;

public class Exercise {
    public String name;
    public String repetitions;

    public Exercise(String name, List<Repetition> repetitions) {
        this.name = name;
        this.repetitions = repetitions.stream()
                .filter(Objects::nonNull)
                .map(Repetition::toString)
                .collect(joining("'"));
    }

    public List<Repetition> getRepetitions() {
        return Arrays.stream(repetitions.split("'"))
                .map(Repetition::parse)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return fixedLengthString(name, 15) + " " + Optional.ofNullable(getRepetitions())
                .filter(reps -> !reps.isEmpty())
                .map(reps -> reps.get(0))
                .map(rep -> fixedLengthString(rep.weight + " kg", 14) + fixedLengthString(String.valueOf(rep.repetitions), 25))
                .orElse("");
    }

    public String toStorageString() {
        return name + "$" + repetitions;
    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%-" + length + "." + length + "s", string);
    }

    public static Exercise parse(String input) {
        if (isNull(input) || input.isEmpty())
            return null;
        String[] split = input.split("\\$");
        if (split.length == 1)
            return split[0].contains("~")
                    ?
                    new Exercise("", Arrays.stream(split[0].split("'"))
                            .map(String::trim)
                            .map(Repetition::parse)
                            .collect(Collectors.toList()))
                    :
                    new Exercise(split[0], new ArrayList<>());
        return new Exercise(split[0], Arrays.stream(split[1].split("'"))
                .map(String::trim)
                .map(Repetition::parse)
                .collect(Collectors.toList()));
    }
}
