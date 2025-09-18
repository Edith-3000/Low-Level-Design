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
}
