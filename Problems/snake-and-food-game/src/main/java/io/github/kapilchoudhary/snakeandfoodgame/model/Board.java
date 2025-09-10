package io.github.kapilchoudhary.snakeandfoodgame.model;

import io.github.kapilchoudhary.snakeandfoodgame.model.food.*;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

public class Board {
    @Getter private final int height; // #rows
    @Getter private final int width; // #cols
    private final List<List<BoardCell>> boardCells;
    private static Board instance;

    List<List<BoardCell>> getBoardCells() {
        return Collections.unmodifiableList(boardCells);
    }

    private Board(final int height, final int width) {
        this.height = height;
        this.width = width;

        this.boardCells = new ArrayList<>();

        for (int row = 0; row < height; row++) {
            List<BoardCell> list = new ArrayList<>();

            for (int col = 0; col < width; col++) {
                BoardCell boardCell = new BoardCell(row, col);
                list.add(boardCell);
            }

            this.boardCells.add(list);
        }
    }

    public static Board getInstance(final int height, final int width) {
        if (instance == null) {
            instance = new Board(height, width);
        }
        return instance;
    }

//    public void addBoardCell(@NonNull final BoardCell boardCell) {
//        int boardCellRow = boardCell.getRow();
//        int boardCellCol = boardCell.getCol();
//
//        boardCells.get(boardCellRow).set(boardCellCol, boardCell);
//    }

    public BoardCell getBoardCellWithDimensions(final int row, final int col) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i == row) && (j == col)) {
                    return boardCells.get(i).get(j);
                }
            }
        }

        // If no board cell found with for given row, col combination
        return new BoardCell(row, col);
    }

    public boolean boardBoundaryCrossed(@NonNull final BoardCell boardCell) {
        return boardCell.getRow() < 0 ||
                boardCell.getRow() >= height ||
                boardCell.getCol() < 0 ||
                boardCell.getCol() >= width;
    }

    public BoardCell wrapBoardCell(final int row, final int col) {
        int newRow = row;
        int newCol = col;

        if (row < 0) {
            newRow = height - 1;
        } else if (row >= height) {
            newRow = 0;
        }

        if (col < 0) {
            newCol = width - 1;
        } else if (col >= width) {
            newCol = 0;
        }

        return getBoardCellWithDimensions(newRow, newCol);
    }

    public void placeRandomFood(@NonNull final Map<BoardCell, Boolean> snakeBodyMap) {
        Food randomFood = RandomFoodGenerator.generateRandomFood();
        Random random = new Random();

        BoardCell cell;

        do {
            int row = random.nextInt(height);
            int col = random.nextInt(width);
            cell = getBoardCellWithDimensions(row, col);
        } while ((snakeBodyMap.containsKey(cell)) || (cell.getFood() != null));

        cell.setFood(randomFood);

        System.out.println("Food: " + randomFood + " placed at: [" + cell.getRow() + ", " + cell.getCol() + "]");
    }

    public void printBoard(@NonNull final Snake snake) {
//        clearConsole();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                BoardCell cell = boardCells.get(i).get(j);

                if (snake.getHead().equals(cell)) {
                    // System.out.print("H ");  // Head of snake
                    System.out.print("\uD83D\uDC0D ");   // Snake head
                } else if (snake.getSnakebodyMap().containsKey(cell)) {
                    // System.out.print("S ");  // Snake body
                    System.out.print("⚫ ");
                } else if (cell.getFood() != null) {
                    Food food = cell.getFood();
                    // String foodName = food.getClass().getSimpleName();
                    // System.out.print(foodName.charAt(0) + " "); // e.g., A for Apple, K for Kiwi, M for Mushroom
                    if (food instanceof Apple) {
                        System.out.print("🍎 ");
                    } else if (food instanceof Kiwi) {
                        System.out.print("🥝 ");
                    } else if (food instanceof Mushroom) {
                        System.out.print("🍄 ");
                    }
                } else {
                    // System.out.print(". ");  // Empty space
                    System.out.print("⬜ ");   // Empty spaces
                }
            }
            System.out.println();
        }
        System.out.println();
    }

//    private void clearConsole() {
//        try {
//            System.out.print("\033[H\033[2J");
//            System.out.flush();
//        } catch (Exception e) {
//            // In case clearing fails, just print some newlines
//            for (int i = 0; i < 50; i++) {
//                System.out.println();
//            }
//        }
//    }
}
