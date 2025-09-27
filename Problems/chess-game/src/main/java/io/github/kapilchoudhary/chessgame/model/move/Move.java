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

    /**
     * @param o   the reference object with which to compare.
     * @return boolean
     */
    // This had to be done, otherwise the move made by player using `currentPlayer.getPlayerMovementStrategy().makeMove`
    // will never be equated with any move returned by `board.getAllLegalMoves()`, even though it is a valid legal move.
    // We do not want to compare 2 moves just on the basis of their references.
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
