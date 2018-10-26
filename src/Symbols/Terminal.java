package Symbols;

import java.util.Arrays;
import java.util.Optional;

public enum Terminal implements Symbol {
    EOF(0),
    ERROR(99);

    private final int id;

    Terminal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Terminal valueOf(int value) {
        return Arrays.stream(values())
                .filter(terminal -> terminal.id == value)
                .findFirst().orElse(ERROR);
    }
}
