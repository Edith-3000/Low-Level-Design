package io.github.kapilchoudhary.chessgame;

import io.github.kapilchoudhary.chessgame.controller.ChessGame;
import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import io.github.kapilchoudhary.chessgame.strategy.playermovement.AIMovement;
import io.github.kapilchoudhary.chessgame.strategy.playermovement.HumanMovement;
import io.github.kapilchoudhary.chessgame.strategy.playermovement.PlayerMovementStrategy;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PlayerMovementStrategy humanPlayerMovementStrategy = new HumanMovement(scanner);
        PlayerMovementStrategy aiPlayerMovementStrategy = new AIMovement();

        Player playerA = new Player("1-trump", "TRUMP", PieceType.WHITE);
        playerA.setPlayerMovementStrategy(humanPlayerMovementStrategy);

        Player playerB = new Player("2-gpt", "CHAT-GPT", PieceType.BLACK);
        playerB.setPlayerMovementStrategy(aiPlayerMovementStrategy);

        Board.init(8, 8);
        Board board = Board.getBoardInstance();

        board.defaultBoardSetup();

        ChessGame chessGame = new ChessGame();
        chessGame.setBoard(board);
        chessGame.setPlayerA(playerA);
        chessGame.setPlayerB(playerB);

        chessGame.start();
    }
}
