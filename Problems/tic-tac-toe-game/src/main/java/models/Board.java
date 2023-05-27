package models;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private final Mark[][] board;

    public Board(int size) {
        this.size = size;
        board = new Mark[size][size];
    }

    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] != null) {
                    System.out.print(board[i][j].getMarkType().name() + "   ");
                }
                else System.out.print("    ");

                System.out.print(" | ");
            }

            System.out.println();
        }
    }

    public List<Pair<Integer, Integer>> getFreeCells() {
        List<Pair<Integer, Integer>> freeCells = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] == null) {
                    Pair<Integer, Integer> rowColumn = new Pair<>(i, j);
                    freeCells.add(rowColumn);
                }
            }
        }

        return freeCells;
    }

    public boolean addMark(Mark mark, int row, int column) {
        if(board[row][column] != null) return false;
        board[row][column] = mark;
        return true;
    }

    public int getSize() {
        return size;
    }

    public Mark getCellValue(int row, int column) {
        return board[row][column];
    }
}
