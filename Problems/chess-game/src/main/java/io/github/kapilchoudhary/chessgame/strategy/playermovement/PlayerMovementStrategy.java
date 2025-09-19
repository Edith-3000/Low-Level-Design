package io.github.kapilchoudhary.chessgame.strategy.playermovement;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import lombok.NonNull;

public interface PlayerMovementStrategy {
    Move makeMove(@NonNull final Board board, @NonNull final PieceType pieceType, final Move lastMove);
}
