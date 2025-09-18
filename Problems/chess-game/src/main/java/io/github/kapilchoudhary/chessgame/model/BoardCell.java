package io.github.kapilchoudhary.chessgame.model;

import io.github.kapilchoudhary.chessgame.enums.CellType;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class BoardCell {
    @Getter private final int row;
    @Getter private final int col;
    @Getter private final CellType cellType;
    @Getter @Setter private Piece piece;

    public BoardCell(final int row, final int col, @NonNull final CellType cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
        this.piece = null;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")" + (piece != null ? "[" + piece + "]" : "[]");
    }
}
