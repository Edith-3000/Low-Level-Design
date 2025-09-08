package io.github.kapilchoudhary.snakeandfoodgame.model;

import io.github.kapilchoudhary.snakeandfoodgame.model.food.Food;
import lombok.Getter;
import lombok.Setter;

public class BoardCell {
    @Getter private final int row;
    @Getter private final int col;
    @Getter @Setter private Food food;

    public BoardCell(final int row, final int col) {
        this.row = row;
        this.col = col;
    }
}
