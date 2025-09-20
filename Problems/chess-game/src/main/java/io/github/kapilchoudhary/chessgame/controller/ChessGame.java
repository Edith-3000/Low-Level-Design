package io.github.kapilchoudhary.chessgame.controller;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.CastlingMove;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame {
    private final List<Move> gameMoves;
    private boolean running;
    @Getter @Setter private Player playerA;
    @Getter @Setter private Player playerB;
    private Player currentPlayer;
    @Setter private Board board;
    private Player winner;
    private boolean isDraw;

    public ChessGame() {
        this.gameMoves = new ArrayList<>();
        this.running = true;
        this.winner = null;
        this.isDraw = false;
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(gameMoves);
    }

    public void start() {
        if (playerA.getPieceType() == PieceType.WHITE) {
            currentPlayer = playerA;
        } else {
            currentPlayer = playerB;
        }

        while (running) {
            board.displayBoard();

            Move lastMove = null;
            if (!gameMoves.isEmpty()) {
                lastMove = gameMoves.get(gameMoves.size() - 1);
            }

            BoardCell kingCell = board.getKingCell(currentPlayer.getPieceType());

            // This is check
            if (board.isCellUnderAttack(kingCell, lastMove)) {
                List<Move> kingLegalMoves = kingCell.getPiece().getPieceMovementStrategy().getLegalMoves(kingCell, lastMove, board);
                List<Move> validKingLegalMoves = new ArrayList<>();

                // Since Castling not allowed when King in check
                for (Move move: kingLegalMoves) {
                    if (!(move instanceof CastlingMove)) {
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
                        board.applyMove(currentMove, currentPlayer);
                        gameMoves.add(currentMove);
                    }

                    changePlayerTurn();
                }
            } else {
                Move currentMove = currentPlayer.getPlayerMovementStrategy().makeMove(board, currentPlayer.getPieceType(), lastMove);

                // No move left with current player, STALEMATE condition i.e. currentPlayer's King not in check and currentPlayer has 0 legal moves
                if (currentMove == null) {
                    isDraw = true;
                    end();
                } else if (!isValidMove(currentMove, lastMove)) {
                    System.out.println("Invalid move, try again!");
                } else {
                    board.applyMove(currentMove, currentPlayer);
                    gameMoves.add(currentMove);
                }

                changePlayerTurn();
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

        if (isDraw) {
            System.out.println("It's a DRAW!");
        } else {
            System.out.println("Player: " + winner.getId() + " won!");
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
