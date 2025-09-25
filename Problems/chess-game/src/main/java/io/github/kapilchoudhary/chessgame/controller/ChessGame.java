package io.github.kapilchoudhary.chessgame.controller;

import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import lombok.Getter;
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
        setStartPlayer();

        while (running) {
            board.displayBoard();

            Move lastMove = getLastMove();

            List<Move> legalMoves = board.getAllLegalMoves(currentPlayer.getPieceType(), lastMove);

            if (legalMoves.isEmpty()) {
                if (board.isCellUnderAttack(board.getKingCell(currentPlayer.getPieceType()))) {
                    // Checkmate
                    winner = currentPlayer == playerA ? playerB : playerA;
                } else {
                    // Stalemate
                    isDraw = true;
                }
                end();
                break;
            }

            System.out.println("**********************************************************************************************************************");
            System.out.println("Current Player: " + currentPlayer);
            System.out.print("Valid moves for current player: ");
            board.displayMoves(legalMoves);
            Move currentMove = currentPlayer.getPlayerMovementStrategy().makeMove(board, currentPlayer.getPieceType(), lastMove);

            if (!legalMoves.contains(currentMove)) {
                System.out.println("Invalid move, try again!");
                continue;
            }

            board.applyMove(currentMove, currentPlayer);
            gameMoves.add(currentMove);

            changePlayerTurn();
        }
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
        currentPlayer = (currentPlayer == playerA) ? playerB : playerA;
    }

    private void setStartPlayer() {
        currentPlayer = (playerA.getPieceType() == PieceType.WHITE)
                ? playerA
                : playerB;
    }

    private Move getLastMove() {
        Move lastMove = null;
        if (!gameMoves.isEmpty()) {
            lastMove = gameMoves.get(gameMoves.size() - 1);
        }

        return lastMove;
    }

//    private boolean isValidMove(@NonNull final Move move, final Move lastMove) {
//        BoardCell sourceCell = move.getSourceCell();
//        BoardCell targetCell = move.getTargetCell();
//
//        List<Move> sourceCellLegalMoves = sourceCell.getPiece().getPieceMovementStrategy().getLegalMoves(sourceCell, lastMove, board);
//
//        for (Move legalMove: sourceCellLegalMoves) {
//            // No override of equals() and hasCode() has been done for BoardCell, thereby equality will be
//            // on the basis of reference, it will be correct since we only initialise a BoardCell once to a [row, col]
//            if (targetCell == legalMove.getTargetCell()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}
