package io.github.kapilchoudhary.chessgame.model.move;

import io.github.kapilchoudhary.chessgame.model.BoardCell;
import lombok.Getter;
import lombok.NonNull;

public class Move {
    @Getter protected final BoardCell sourceCell;
    @Getter protected final BoardCell targetCell;

    public Move(@NonNull final BoardCell sourceCell, @NonNull final BoardCell targetCell) {
        this.sourceCell = sourceCell;
        this.targetCell = targetCell;
    }

    // This had to be done, otherwise the move made by player using `currentPlayer.getPlayerMovementStrategy().makeMove`
    // will never be equated with any move returned by `board.getAllLegalMoves()`, even though it is a valid legal move
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move other = (Move) o;
        return (this.sourceCell == other.getSourceCell()) && (this.targetCell == other.targetCell);
    }

    @Override
    public int hashCode() {
        return sourceCell.hashCode() + (targetCell.hashCode() * 31);
    }
}
