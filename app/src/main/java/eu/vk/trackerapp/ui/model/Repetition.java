package eu.vk.trackerapp.ui.model;

import static java.util.Objects.isNull;

public class Repetition {
    public double weight;
    public int repetitions;

    public Repetition(double weight, int repetitions) {
        this.weight = weight;
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return weight + "~" + repetitions;
    }

    public static Repetition parse(String input) {
        try {
            if (isNull(input) || input.isEmpty())
                return null;
            String[] split = input.split("~");
            if (split.length == 1)
                return split[0].contains(".")
                        ? new Repetition(Double.parseDouble(split[0]), 0)
                        : new Repetition(0, Integer.parseInt(split[0]));
            return new Repetition(Double.parseDouble(split[0]), Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
