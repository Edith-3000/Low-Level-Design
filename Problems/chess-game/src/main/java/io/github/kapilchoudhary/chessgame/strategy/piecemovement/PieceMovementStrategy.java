package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import lombok.NonNull;

import java.util.List;

public interface PieceMovementStrategy {
    /**
     * Returns the set of board cells that this piece could potentially attack
     * from the given source cell, ignoring king safety rules.
     *
     * - Used primarily for check/checkmate detection.
     * - Includes squares the piece controls, even if moving there would leave
     *   its own king in check (e.g., pinned pieces still "attack"), or if it is attacking its own team piece.
     * - Example: a pawn's attack cells are diagonally forward squares,
     *   regardless of whether those moves are currently legal.
     */
    List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board);

    /**
     * Returns all legal moves for this piece from the given source cell,
     * considering the current board state and king safety.
     *
     * - A legal move must follow the piece's movement rules AND must not
     *   leave the player's own king in check.
     * - The last move is provided to support special rules like en passant
     *   and castling.
     * - Example: a rook blocked by its own piece has no legal move to capture
     *   its own team's piece.
     */
    List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board);
}

