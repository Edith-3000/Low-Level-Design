package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RookMovement implements PieceMovementStrategy {
    @Override
    public List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board) {
        List<Move> legalMoves = new ArrayList<>();

        Piece rook = sourceCell.getPiece();
        if ((rook == null) || lastMove == null) {
            return legalMoves;
        }

        for (Direction direction: Direction.getRookDirections()) {
            SlidingMovementHelper.addSlidingLegalMoves(sourceCell, direction, legalMoves, lastMove);
        }

        return legalMoves;
    }

    @Override
    public List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board) {
        List<BoardCell> attackCells = new ArrayList<>();

        Piece rook = sourceCell.getPiece();
        if (rook == null) {
            return attackCells;
        }

        for (Direction direction: Direction.getRookDirections()) {
            SlidingMovementHelper.addSlidingAttackCells(sourceCell, direction, attackCells);
        }

        return attackCells;
    }
}
