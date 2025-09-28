package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.List;

public final class SlidingMovementHelper {
    public static void addSlidingLegalMoves(@NonNull final BoardCell sourceCell, @NonNull final Direction direction, @NonNull final List<Move> legalMoves, final Move lastMove) {
        Board board = Board.getBoardInstance();
        Piece piece = sourceCell.getPiece();

        if (piece == null) {
            return;
        }

        int nr = sourceCell.getRow();
        int nc = sourceCell.getCol();

        while (true) {
            nr += direction.getDeltaRow();
            nc += direction.getDeltaCol();

            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                break;
            }

            if ((targetCell.getPiece() == null) || piece.isOpponent(targetCell.getPiece())) {
                if (board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
                    break;
                }
            }

            Piece targetCellPiece = targetCell.getPiece();
            if (targetCellPiece == null) {
                legalMoves.add(new Move(sourceCell, targetCell));
            } else {
                if (piece.isOpponent(targetCellPiece)) {
                    legalMoves.add(new Move(sourceCell, targetCell));
                }

                break;
            }
        }
    }

    public static void addSlidingAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Direction direction, @NonNull final List<BoardCell> attackCells) {
        Board board = Board.getBoardInstance();
        Piece piece = sourceCell.getPiece();

        if (piece == null) {
            return;
        }

        int nr = sourceCell.getRow();
        int nc = sourceCell.getCol();

        while (true) {
            nr += direction.getDeltaRow();
            nc += direction.getDeltaCol();

            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                break;
            }

            // Add the cell regardless of whether it is empty, opponent, or friendly
            attackCells.add(targetCell);

            // Stop sliding further if any piece blocks
            if (targetCell.getPiece() != null) {
                break;
            }
        }
    }
}
