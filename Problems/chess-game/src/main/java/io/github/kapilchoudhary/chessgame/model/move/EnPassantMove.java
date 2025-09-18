package io.github.kapilchoudhary.chessgame.model.move;

import io.github.kapilchoudhary.chessgame.model.BoardCell;
import lombok.Getter;
import lombok.NonNull;

public class EnPassantMove extends Move {
    @Getter private final BoardCell capturedPawnCell;

    public EnPassantMove(@NonNull final BoardCell sourceCell, @NonNull final BoardCell targetCell, @NonNull final BoardCell opponentPawnCell) {
        super(sourceCell, targetCell);
        this.capturedPawnCell = opponentPawnCell;
    }
}
