package io.github.kapilchoudhary.chessgame.model.piece;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.strategy.piecemovement.PieceMovementStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class Piece {
    @Getter protected final PieceType pieceType;
    @Getter protected boolean isCaptured;
    @Getter @Setter protected boolean hasMoved;
    @Getter @Setter protected PieceMovementStrategy pieceMovementStrategy;

    public Piece(@NonNull final PieceType pieceType) {
        this.pieceType = pieceType;
        this.isCaptured = false;
        this.hasMoved = false;
        this.pieceMovementStrategy = null;
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
