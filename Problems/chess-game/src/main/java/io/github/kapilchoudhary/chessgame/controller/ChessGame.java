package io.github.kapilchoudhary.chessgame.controller;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame {
    private final List<Move> moves;
    private boolean running;
    @Getter @Setter private Player playerA;
    @Getter @Setter private Player playerB;
    private Player currentPlayer;
    @Setter private Board board;
    private Player winner;

    public ChessGame() {
        this.moves = new ArrayList<>();
        this.running = true;
        this.winner = null;
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public void start() {
        if (playerA.getPieceType() == PieceType.WHITE) {
            currentPlayer = playerA;
        } else {
            currentPlayer = playerB;
        }

        while (running) {
            Move lastMove = null;
            if (!moves.isEmpty()) {
                lastMove = moves.get(moves.size() - 1);
            }

            BoardCell kingCell = board.getKingCell(currentPlayer.getPieceType());
            if (board.isCellUnderAttack(kingCell, lastMove)) {
                List<Move> kingLegalMoves = kingCell.getPiece().getPieceMovementStrategy().getLegalMoves(kingCell, lastMove, board);

                if (kingLegalMoves.isEmpty()) {
                    if (currentPlayer == playerA) {
                        winner = playerB;
                    } else {
                        winner = playerA;
                    }

                    end();
                }
            }

            Move currentMove = currentPlayer.getPlayerMovementStrategy().makeMove(board, currentPlayer.getPieceType(), lastMove);

            if (currentMove == null) {
                // TODO: what to do?
            }

            if (isValidMove(currentMove, lastMove)) {

            }
        }
    }

    private boolean isValidMove(@NonNull final Move move, final Move lastMove) {
        BoardCell sourceCell = move.getSourceCell();
        BoardCell targetCell = move.getTargetCell();

        List<Move> sourceCellLegalMoves = sourceCell.getPiece().getPieceMovementStrategy().getLegalMoves(sourceCell, lastMove, board);

        for (Move legalMove: sourceCellLegalMoves) {
            // No override of equals() and hasCode() has been done for BoardCell, thereby equality will be
            // on the basis of reference, it will be correct since we only initialise a BoardCell once to a [row, col]
            if (targetCell == legalMove.getTargetCell()) {
                return true;
            }
        }

        return false;
    }

    private void end() {
        running = false;
        System.out.println("Player: " + winner.getId() + " won!");
    }
}
