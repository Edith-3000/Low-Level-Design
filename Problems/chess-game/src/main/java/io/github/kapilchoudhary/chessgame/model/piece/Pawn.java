package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class Pawn extends Piece {
    public Pawn(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
