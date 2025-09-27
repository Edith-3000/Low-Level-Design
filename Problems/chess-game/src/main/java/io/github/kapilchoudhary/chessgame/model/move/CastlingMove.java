package io.github.kapilchoudhary.chessgame.model.move;

import io.github.kapilchoudhary.chessgame.model.BoardCell;
import lombok.Getter;
import lombok.NonNull;

public class CastlingMove extends Move {
    @Getter private final BoardCell rookFrom;
    @Getter private final BoardCell rookTo;

    public CastlingMove(@NonNull final BoardCell sourceCell, @NonNull final BoardCell targetCell, @NonNull final BoardCell rookFrom, @NonNull final BoardCell rookTo) {
        super(sourceCell, targetCell);
        this.rookFrom = rookFrom;
        this.rookTo = rookTo;
    }
}
