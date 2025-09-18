package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class Queen extends Piece {
    public Queen(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
