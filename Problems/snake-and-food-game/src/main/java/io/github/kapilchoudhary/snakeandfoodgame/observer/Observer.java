package io.github.kapilchoudhary.snakeandfoodgame.observer;

public interface Observer {
    void onScoreUpdate(final int score);
    void onGameOver(final int score);
}
