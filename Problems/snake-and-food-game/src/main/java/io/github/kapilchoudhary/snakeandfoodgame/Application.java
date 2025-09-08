package io.github.kapilchoudhary.snakeandfoodgame;

import io.github.kapilchoudhary.snakeandfoodgame.controller.GameController;
import io.github.kapilchoudhary.snakeandfoodgame.enums.Direction;
import io.github.kapilchoudhary.snakeandfoodgame.model.Board;
import io.github.kapilchoudhary.snakeandfoodgame.model.BoardCell;
import io.github.kapilchoudhary.snakeandfoodgame.model.Snake;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(10, 10);
        BoardCell snakeBodyInitCell = board.getBoardCellWithDimensions(0, 0);

        Snake snake = new Snake(snakeBodyInitCell, Direction.RIGHT);

        board.placeRandomFood(snake.getSnakebodyMap());

        GameController gameController = new GameController(board, snake, true);
        gameController.start();
    }
}
