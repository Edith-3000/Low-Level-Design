package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import lombok.Getter;
import lombok.NonNull;

public abstract class Piece {
    @Getter protected final PieceType pieceType;
    @Getter protected boolean isCaptured;
    @Getter protected boolean hasMoved;

    public Piece(@NonNull final PieceType pieceType) {
        this.pieceType = pieceType;
        this.isCaptured = false;
        this.hasMoved = false;
    }

    public void capture() {
        isCaptured = true;
    }

    public boolean isOpponent(@NonNull final Piece other) {
        return pieceType != other.pieceType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{color=" + pieceType + "}";
    }
}
