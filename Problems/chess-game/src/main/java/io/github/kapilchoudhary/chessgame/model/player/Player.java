package io.github.kapilchoudhary.chessgame.model.player;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.strategy.playermovement.PlayerMovementStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class Player {
    @Getter protected final String id;
    @Getter protected final String name;
    @Getter protected final PieceType pieceType;
    @Getter @Setter protected PlayerMovementStrategy playerMovementStrategy;

    public Player(@NonNull final String id, @NonNull final String name, @NonNull final PieceType pieceType) {
        this.id = id;
        this.name = name;
        this.pieceType = pieceType;
    }
}
