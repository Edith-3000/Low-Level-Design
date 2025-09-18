package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.NonNull;

public class Knight extends Piece {
    public Knight(@NonNull final PieceType pieceType) {
        super(pieceType);
    }
}
