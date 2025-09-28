package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class KnightMovement implements PieceMovementStrategy {
    @Override
    public List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board) {
//        Board board = Board.getBoardInstance();
        List<Move> legalMoves = new ArrayList<>();

        Piece knight = sourceCell.getPiece();
        if (knight == null) {
            return legalMoves;
        }

        for (Direction direction: Direction.getKnightDirections()) {
            int nr = sourceCell.getRow() + direction.getDeltaRow();
            int nc = sourceCell.getCol() + direction.getDeltaCol();

            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                continue;
            }

            if ((targetCell.getPiece() == null) || knight.isOpponent(targetCell.getPiece())) {
                if (board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
                    continue;
                }
            }

            if ((targetCell.getPiece() == null) || knight.isOpponent(targetCell.getPiece())) {
                legalMoves.add(new Move(sourceCell, targetCell));
            }
        }

        return legalMoves;
    }

    @Override
    public List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board) {
        List<BoardCell> attackCells = new ArrayList<>();

        Piece knight = sourceCell.getPiece();
        if (knight == null) {
            return attackCells;
        }

        for (Direction direction: Direction.getKnightDirections()) {
            SlidingMovementHelper.addSlidingAttackCells(sourceCell, direction, attackCells);
        }

        return attackCells;
    }
}
