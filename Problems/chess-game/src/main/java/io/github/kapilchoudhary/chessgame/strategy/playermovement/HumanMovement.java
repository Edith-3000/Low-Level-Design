package io.github.kapilchoudhary.chessgame.strategy.playermovement;

import io.github.kapilchoudhary.chessgame.constants.AppConstants;
import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import lombok.NonNull;

import java.util.Scanner;

public class HumanMovement implements PlayerMovementStrategy {
//    private final int expectedInputLength = 5;
    private final Scanner scanner;

    public HumanMovement() {
        this.scanner = new Scanner(System.in);;
    }

    @Override
    public Move makeMove(@NonNull final Board board, @NonNull final PieceType pieceType, final Move lastMove) {
        System.out.print("[" + this.getClass().getSimpleName() + " Strategy] type move in the format (source target) and hit enter: (for example a5 d7) ");

        String input = scanner.nextLine().trim();

        String[] splitInput = input.split("\\s+");

        if (splitInput.length != 2 ||
                splitInput[0].length() != 2 ||
                splitInput[1].length() != 2) {
            throw new IllegalArgumentException("Invalid input format. Example: a2 a4");
        }

        char srcMoveFile = splitInput[0].charAt(0);
        char srcMoveRank = splitInput[0].charAt(1);

        char targetMoveFile = splitInput[1].charAt(0);
        char targetMoveRank = splitInput[1].charAt(1);

//        if (!board.isValidFile(srcMoveFile) || !(board.isValidRank(srcMoveRank)) ||
//                !board.isValidFile(targetMoveFile) || !(board.isValidRank(targetMoveRank))
//        ) {
//            throw new IllegalArgumentException("Invalid move, try again!");
//        }

        int srcMoveRow = board.rankToRow(srcMoveRank);
        int srcMoveCol = board.fileToCol(srcMoveFile);

        int targetMoveRow = board.rankToRow(targetMoveRank);
        int targetMoveCol = board.fileToCol(targetMoveFile);

        BoardCell srcMoveCell = board.getBoardCell(srcMoveRow, srcMoveCol);
        BoardCell targetMoveCell = board.getBoardCell(targetMoveRow, targetMoveCol);

        Move move = new Move(srcMoveCell, targetMoveCell);

        return move;
    }
}
