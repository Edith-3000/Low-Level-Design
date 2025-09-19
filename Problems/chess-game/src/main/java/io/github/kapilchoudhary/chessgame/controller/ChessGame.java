package io.github.kapilchoudhary.chessgame.controller;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.CastlingMove;
import io.github.kapilchoudhary.chessgame.model.move.EnPassantMove;
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

            // This is check
            if (board.isCellUnderAttack(kingCell, lastMove)) {
                List<Move> kingLegalMoves = kingCell.getPiece().getPieceMovementStrategy().getLegalMoves(kingCell, lastMove, board);
                List<Move> validKingLegalMoves = new ArrayList<>();

                // Since King cannot capture when in check
                for (Move move: kingLegalMoves) {
                    if ((move.getTargetCell().getPiece() == null) && !(move instanceof CastlingMove)) {
                        validKingLegalMoves.add(move);
                    }
                }

                // This is checkmate
                if (validKingLegalMoves.isEmpty()) {
                    if (currentPlayer == playerA) {
                        winner = playerB;
                    } else {
                        winner = playerA;
                    }
                    end();
                } else {
                    Move currentMove = currentPlayer.getPlayerMovementStrategy().makeMove(board, currentPlayer.getPieceType(), lastMove);

                    if (!validKingLegalMoves.contains(currentMove)) {
                        System.out.println("Invalid move, try again!");
                        continue;
                    } else {
                        applyMove(currentMove);
                    }

                    changePlayerTurn();
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

    private void applyMove(@NonNull final Move move) {
        if (move instanceof CastlingMove) {
            BoardCell rookFrom = ((CastlingMove) move).getRookFrom();
            BoardCell rookTo = ((CastlingMove) move).getRookFrom();
            BoardCell kingFrom = move.getSourceCell();
            BoardCell kingTo = move.getTargetCell();

            rookFrom.getPiece().setHasMoved(true);
            kingFrom.getPiece().setHasMoved(true);

            rookTo.setPiece(rookFrom.getPiece());
            rookFrom.setPiece(null);

            kingTo.setPiece(kingFrom.getPiece());
            kingFrom.setPiece(null);
        } else if (move instanceof EnPassantMove) {
            BoardCell toBeCapturedPawnCell = ((EnPassantMove) move).getCapturedPawnCell();

            BoardCell sourceCell = move.getSourceCell();
            BoardCell targetCell = move.getTargetCell();

            sourceCell.getPiece().setHasMoved(true); // Don't really need it by the way
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.setPiece(null);

            currentPlayer.addCapturedPiece(toBeCapturedPawnCell.getPiece());
            toBeCapturedPawnCell.getPiece().capture();
            toBeCapturedPawnCell.setPiece(null);
        } else {
            BoardCell sourceCell = move.getSourceCell();
            BoardCell targetCell = move.getTargetCell();

            sourceCell.getPiece().setHasMoved(true);
            currentPlayer.addCapturedPiece(targetCell.getPiece());
            targetCell.getPiece().capture();
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.setPiece(null);
        }
    }

    private void changePlayerTurn() {
        if (currentPlayer == playerA) {
            currentPlayer = playerB;
        } else {
            currentPlayer = playerA;
        }
    }
}
