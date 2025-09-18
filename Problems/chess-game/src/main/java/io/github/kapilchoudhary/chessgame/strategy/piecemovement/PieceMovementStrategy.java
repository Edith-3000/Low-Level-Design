package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import lombok.NonNull;

import java.util.List;

public interface PieceMovementStrategy {
    List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, @NonNull final Move lastMove, @NonNull final Board board);
}
