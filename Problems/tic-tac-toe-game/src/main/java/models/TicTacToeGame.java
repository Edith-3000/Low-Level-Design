package models;

import javafx.util.Pair;

import java.util.*;

public class TicTacToeGame {
    private final Queue<Player> playerQueue;
    private final Board gameBoard;

    public TicTacToeGame(final int boardSize, final List<Player> players) {
        playerQueue = new LinkedList<>();

        for(Player player: players) {
            playerQueue.add(player);
        }

        gameBoard = new Board(boardSize);
    }

    public String startGame() {
        if (playerQueue.size() == 0) {
            return "NOT ENOUGH PLAYERS TO PLAY THE GAME";
        }

        boolean endGame = false;

        while(!endGame) {
            gameBoard.printBoard();

            List<Pair<Integer, Integer>> freeCells = gameBoard.getFreeCells();
            if(freeCells.isEmpty()) {
                endGame = true;
                continue;
            }

            Player currentPlayer = playerQueue.peek();
            System.out.print("PLAYER: " + currentPlayer.getName() + " -- ENTER [Row, Column]: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            // inputScanner.close();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            boolean markAddedSuccessfully = gameBoard.addMark(currentPlayer.getMark(), inputRow, inputColumn);
            if(!markAddedSuccessfully) {
                System.out.println("INCORRECT POSITION CHOSEN, TRY AGAIN");
                continue;
            }

            playerQueue.poll();
            playerQueue.add(currentPlayer);

            boolean currentPlayerWins = isCurrentPlayerWinner(inputRow, inputColumn, currentPlayer.getMark());
            if(currentPlayerWins) {
                gameBoard.printBoard();
                return currentPlayer.getName();
            }
        }

        return "TIE";
    }

    boolean isCurrentPlayerWinner(int row, int column, Mark mark) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        for(int j = 0; j < gameBoard.getSize(); j++) {
            Mark cellValue = gameBoard.getCellValue(row, j);
            if((cellValue == null) || (cellValue.getMarkType() != mark.getMarkType())) {
                rowMatch = false;
                break;
            }
        }

        for(int i = 0; i < gameBoard.getSize(); i++) {
            Mark cellValue = gameBoard.getCellValue(i, column);
            if((cellValue == null) || (cellValue.getMarkType() != mark.getMarkType())) {
                columnMatch = false;
                break;
            }
        }

        for(int i = 0, j = 0; i < gameBoard.getSize() && j < gameBoard.getSize(); i++, j++) {
            Mark cellValue = gameBoard.getCellValue(i, j);
            if((cellValue == null) || (cellValue.getMarkType() != mark.getMarkType())) {
                diagonalMatch = false;
                break;
            }
        }

        for(int i = 0, j = gameBoard.getSize() - 1; i < gameBoard.getSize() && j >= 0; i++, j--) {
            Mark cellValue = gameBoard.getCellValue(i, j);
            if((cellValue == null) || (cellValue.getMarkType() != mark.getMarkType())) {
                antiDiagonalMatch = false;
                break;
            }
        }

        return (rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch);
    }
}
