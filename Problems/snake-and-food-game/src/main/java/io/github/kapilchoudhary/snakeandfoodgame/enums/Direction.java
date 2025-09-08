package io.github.kapilchoudhary.snakeandfoodgame.enums;

import lombok.Getter;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    @Getter private final int dx;
    @Getter private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN) ||
                (this == DOWN && other == UP) ||
                (this == LEFT && other == RIGHT) ||
                (this == RIGHT && other == LEFT);
    }
}
