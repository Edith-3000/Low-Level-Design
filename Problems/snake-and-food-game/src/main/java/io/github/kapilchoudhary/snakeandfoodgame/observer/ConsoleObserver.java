package io.github.kapilchoudhary.snakeandfoodgame.observer;

public class ConsoleObserver implements Observer {
    @Override
    public void onScoreUpdate(final int score) {
        System.out.println("Score updated: " + score);
    }

    @Override
    public void onGameOver(final int score) {
        System.out.println("Game over! Final score: " + score);
    }
}
