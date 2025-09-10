package io.github.kapilchoudhary.snakeandfoodgame.controller;

import io.github.kapilchoudhary.snakeandfoodgame.enums.Direction;
import io.github.kapilchoudhary.snakeandfoodgame.enums.FoodType;
import io.github.kapilchoudhary.snakeandfoodgame.model.Board;
import io.github.kapilchoudhary.snakeandfoodgame.model.BoardCell;
import io.github.kapilchoudhary.snakeandfoodgame.model.Snake;
import io.github.kapilchoudhary.snakeandfoodgame.model.food.Food;
import io.github.kapilchoudhary.snakeandfoodgame.observer.Observer;
import io.github.kapilchoudhary.snakeandfoodgame.observer.Subject;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController implements Subject {
    @Getter private final Board board;
    @Getter private final Snake snake;
    @Getter private final boolean hasWalls;
    private volatile boolean running; // volatile for thread safety
    private int score;
    private final List<Observer> observers;

    public GameController(@NonNull final Board board, @NonNull final Snake snake, final boolean hasWalls) {
        this.board = board;
        this.snake = snake;
        this.hasWalls = hasWalls;
        this.running = true;
        this.score = 0;
        this.observers = new ArrayList<>();
    }

    public void start() {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);

            while (running) {
                String input = sc.nextLine();
                input = String.valueOf(Character.toLowerCase(input.charAt(0)));

                Direction inputDirection = switch (input) {
                    case "w" -> Direction.UP;
                    case "s" -> Direction.DOWN;
                    case "a" -> Direction.LEFT;
                    case "d" -> Direction.RIGHT;
                    default -> null;
                };

                // TODO: Also handle the case when snake's current direction = input direction
                if (inputDirection != null) {
                    snake.queueDirection(inputDirection);
                }
            }

            if (!running) {
                sc.close();
            }
        }, "UserInputThread").start();

        while (running) {
            tick();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void tick() {
        snake.applyNextDirection();

        BoardCell currentHead = snake.getHead();
        Direction currentDirection = snake.getCurrentDirection();

        System.out.println("Snake head at: [" + currentHead.getRow() + ", " + currentHead.getCol() + "], direction: " + currentDirection);

        BoardCell nextHead = board.getBoardCellWithDimensions(currentHead.getRow() + currentDirection.getDx(), currentHead.getCol() + currentDirection.getDy());

        boolean boardBoundaryCrossed = false;

        if (hasWalls) {
            boardBoundaryCrossed = board.boardBoundaryCrossed(nextHead);
        } else {
            nextHead = board.wrapBoardCell(nextHead.getRow(), nextHead.getCol());
        }

        boolean snakeBitesItself = snake.bitesItself(nextHead);

        if (boardBoundaryCrossed || snakeBitesItself) {
            running = false;
            notifyGameOver();
            return;
        }

        Food nextHeadFood = nextHead.getFood();

        if (nextHeadFood != null) {
            if (nextHeadFood.getFoodType().equals(FoodType.POISONOUS)) {
                running = false;
                notifyGameOver();
                return;
            }

            score += nextHeadFood.getScore();
            notifyScoreUpdate();

            nextHead.setFood(null);

            snake.moveHead(nextHead);

            board.placeRandomFood(snake.getSnakebodyMap());
        } else {
            snake.moveHead(nextHead);
            snake.moveTail();
        }

        board.printBoard(snake);
    }

//    private void end() {
//        System.out.println("Game over, score: " + score);
//    }

    @Override
    public void addObserver(@NonNull final Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(@NonNull final Observer observer) {
        observers.remove(observer);
    }

    public void notifyScoreUpdate() {
        for (Observer observer: observers) {
            observer.onScoreUpdate(score);
        }
    }

    public void notifyGameOver() {
        for (Observer observer: observers) {
            observer.onGameOver(score);
        }
    }
}
