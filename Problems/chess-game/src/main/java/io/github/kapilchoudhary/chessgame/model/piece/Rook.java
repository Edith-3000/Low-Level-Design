package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class Rook extends Piece {
    public Rook(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
