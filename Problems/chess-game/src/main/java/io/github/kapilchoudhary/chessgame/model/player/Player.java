package io.github.kapilchoudhary.chessgame.model.player;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import io.github.kapilchoudhary.chessgame.strategy.playermovement.PlayerMovementStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @Getter protected final String id;
    @Getter protected final String name;
    @Getter protected final PieceType pieceType;
    @Getter @Setter protected PlayerMovementStrategy playerMovementStrategy;
    private final List<Piece> capturedPieces;

    public Player(@NonNull final String id, @NonNull final String name, @NonNull final PieceType pieceType) {
        this.id = id;
        this.name = name;
        this.pieceType = pieceType;
        this.capturedPieces = new ArrayList<>();
    }

    public void addCapturedPiece(final Piece piece) {
        if (piece != null) {
            capturedPieces.add(piece);
        }
    }
}
