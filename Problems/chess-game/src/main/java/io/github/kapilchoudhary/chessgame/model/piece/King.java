package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class King extends Piece {
    public King(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
