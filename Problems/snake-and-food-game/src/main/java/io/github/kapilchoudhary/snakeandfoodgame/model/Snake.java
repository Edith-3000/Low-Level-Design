package io.github.kapilchoudhary.snakeandfoodgame.model;

import io.github.kapilchoudhary.snakeandfoodgame.enums.Direction;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

public class Snake {
    private final Deque<BoardCell> snakeBody;
    private final Map<BoardCell, Boolean> snakebodyMap;
    @Getter @Setter private Direction currentDirection;
    @Getter private Direction nextDirection;

    public Snake(@NonNull final BoardCell snakeBodyInitCell, @NonNull final Direction currentDirection) {
        snakeBody = new LinkedList<>();
        snakeBody.addFirst(snakeBodyInitCell);

        snakebodyMap = new HashMap<>();
        snakebodyMap.put(snakeBodyInitCell, true);

        this.currentDirection = currentDirection;
        this.nextDirection = currentDirection;
    }

    public Deque<BoardCell> getSnakeBody() {
        return (Deque<BoardCell>) Collections.unmodifiableCollection(snakeBody); // Collections.unmodifiableDeque(snakeBody) could also be used but not found in this Java version.
    }

    public Map<BoardCell, Boolean> getSnakebodyMap() {
        return Collections.unmodifiableMap(snakebodyMap);
    }

    public BoardCell getHead() {
        return snakeBody.peekFirst();
    }

    public BoardCell getTail() {
        return snakeBody.peekLast();
    }

    // To check if the snake bites itself, if the param boardCell becomes the next head
    public boolean bitesItself(@NonNull final BoardCell boardCell) {
        boolean boardCellPartOfSnakeBody = snakebodyMap.containsKey(boardCell);

        BoardCell snakeTail = getTail();

        boolean boardCellIsTail = (boardCell.getRow() == snakeTail.getRow() &&
                                   boardCell.getCol() == snakeTail.getCol());

        return boardCellPartOfSnakeBody && !boardCellIsTail;
    }

    public void moveTail() {
        BoardCell tail = snakeBody.pollLast();
        snakebodyMap.remove(tail);
    }

    public void moveHead(@NonNull final BoardCell newHead) {
        snakeBody.offerFirst(newHead);
        snakebodyMap.put(newHead, true);
    }

    public void applyNextDirection() {
        this.currentDirection = nextDirection;
    }

    public void queueDirection(@NonNull final Direction direction) {
        if (!direction.isOpposite(currentDirection)) {
            nextDirection = direction;
        }
    }
}
