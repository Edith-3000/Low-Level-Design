package io.github.kapilchoudhary.snakeandfoodgame.observer;

import lombok.NonNull;

public interface Subject {
    void addObserver(@NonNull final Observer observer);
    void removeObserver(@NonNull final Observer observer);
}
