package io.github.kapilchoudhary.movieticketbookingappjava.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

@Getter
public class Theatre {

    private final String id;
    private final String name;

    @Getter(AccessLevel.NONE)
    private List<Screen> screens;

    public Theatre(
            @NonNull final String id,
            @NonNull final String name
    ) {
        this.id = id;
        this.name = name;
    }

    public void addScreen(@NonNull final Screen screen) {
        screens.add(screen);
    }

    public List<Screen> getScreens() {
        return Collections.unmodifiableList(screens);
    }
}
