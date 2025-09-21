package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import lombok.NonNull;

import java.util.List;

public interface PieceMovementStrategy {
    List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board);
    List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board);
}
