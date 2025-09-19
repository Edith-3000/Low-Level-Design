package io.github.kapilchoudhary.chessgame.strategy.playermovement;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIMovement implements PlayerMovementStrategy {
    private final Random random = new Random();

    @Override
    public Move makeMove(@NonNull final Board board, @NonNull final PieceType pieceType, final Move lastMove) {
        List<Move> allLegalMoves = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                BoardCell cell = board.getBoardCell(row, col);
                Piece piece = cell.getPiece();

                if ((piece != null) && (piece.getPieceType() == pieceType)) {
                    allLegalMoves.addAll(piece.getPieceMovementStrategy().getLegalMoves(cell, lastMove, board));
                }
            }
        }

        if (allLegalMoves.isEmpty()) {
            return null;
        }

        return allLegalMoves.get(random.nextInt(allLegalMoves.size()));
    }
}
