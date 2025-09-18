package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.List;

public final class SlidingMovementHelper {
    public static void addSlidingLegalMoves(@NonNull final BoardCell sourceCell, @NonNull final Direction direction, @NonNull final List<Move> legalMoves) {
        Board board = Board.getBoardInstance();
        Piece piece = sourceCell.getPiece();

        if (piece == null) {
            return;
        }

        int nr = sourceCell.getRow() + direction.getDeltaRow();
        int nc = sourceCell.getCol() + direction.getDeltaCol();

        while (!board.outOfBoundary(nr, nc)) {
            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                break;
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

            nr += direction.getDeltaRow();
            nc += direction.getDeltaCol();
        }
    }
}
