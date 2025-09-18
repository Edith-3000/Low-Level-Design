package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class Bishop extends Piece {
    public Bishop(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
