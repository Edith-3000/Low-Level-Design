package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.constants.AppConstants;
import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.CastlingMove;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.King;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import io.github.kapilchoudhary.chessgame.model.piece.Rook;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class KingMovement implements PieceMovementStrategy {
    @Override
    public List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board) {
//        Board board = Board.getBoardInstance();
        List<Move> legalMoves = new ArrayList<>();

        Piece king = sourceCell.getPiece();
        if ((king == null) || (lastMove == null)) {
            return legalMoves;
        }

        for (Direction direction: Direction.getKingDirections()) {
            int nr = sourceCell.getRow() + direction.getDeltaRow();
            int nc = sourceCell.getCol() + direction.getDeltaCol();

            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                continue;
            }

            if ((targetCell.getPiece() == null) || king.isOpponent(targetCell.getPiece())) {
                // Simulate move: king moves to target -> cannot be in check
                if (!board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
                    legalMoves.add(new Move(sourceCell, targetCell));
                }
            }

            // Castling moves (king-side and queen-side)
//            legalMoves.addAll(getCastlingMoves(sourceCell, lastMove));
        }

        // Castling moves (king-side and queen-side)
        legalMoves.addAll(getCastlingMoves(sourceCell));

        return legalMoves;
    }

    @Override
    public List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board) {
        List<BoardCell> attackCells = new ArrayList<>();

        Piece king = sourceCell.getPiece();
        if (king == null) {
            return attackCells;
        }

        for (Direction direction: Direction.getKingDirections()) {
            int nr = sourceCell.getRow() + direction.getDeltaRow();
            int nc = sourceCell.getCol() + direction.getDeltaCol();

            BoardCell targetCell = board.getBoardCell(nr, nc);

            if (targetCell == null) {
                continue;
            }

            attackCells.add(targetCell);
        }

        return attackCells;
    }

    public List<Move> getCastlingMoves(@NonNull final BoardCell sourceCell) {
        List<Move> castlingMoves = new ArrayList<>();

        Board board = Board.getBoardInstance();
        int totalCols = board.getColumns();

        int kingRow = sourceCell.getRow();
        int kingCol = sourceCell.getCol();

        Piece king = sourceCell.getPiece();

        if (!(king instanceof King) || king.isHasMoved() || board.isCellUnderAttack(sourceCell)) {
            return castlingMoves;
        }

        PieceType kingPieceType = king.getPieceType();

        BoardCell rightMostCell = board.getBoardCell(kingRow, totalCols - 1);
        Piece rightMostPiece = rightMostCell.getPiece();

//        BoardCell rightRookLandingCell = board.getBoardCell(kingRow, kingCol - 1);
//        Piece rightRookLandingPiece = null;
//
//        if (rightRookLandingCell != null) {
//            rightRookLandingPiece = rightRookLandingCell.getPiece();
//        }

        boolean allRightPiecesEmpty = true;

        if ((rightMostPiece instanceof Rook)
                && (!rightMostPiece.isHasMoved())
                && !king.isOpponent(rightMostPiece)
        ) {
            for (int col = kingCol + 1; col < rightMostCell.getCol(); col++) {
                BoardCell boardCell = board.getBoardCell(kingRow, col);
                if (boardCell.getPiece() != null) {
                    allRightPiecesEmpty = false;
                    break;
                }
            }

            if (allRightPiecesEmpty) {
                BoardCell rightBoardCell1 = board.getBoardCell(kingRow, kingCol + 1);
                boolean rightUnderAttack1 = board.isCellUnderAttack(rightBoardCell1);

                BoardCell rightBoardCell2 = board.getBoardCell(kingRow, kingCol + 2);
                boolean rightUnderAttack2 = board.isCellUnderAttack(rightBoardCell2);

                if (!rightUnderAttack1 && !rightUnderAttack2) {
                    castlingMoves.add(new CastlingMove(sourceCell, rightBoardCell2, rightMostCell, rightBoardCell1));
                }
            }
        }

        BoardCell leftMostCell = board.getBoardCell(kingRow, AppConstants.COL_ZERO);
        Piece leftMostPiece = leftMostCell.getPiece();

//        BoardCell leftRookLandingCell = board.getBoardCell(kingRow, kingCol + 1);
//        Piece leftRookLandingPiece = null;
//
//        if (leftRookLandingCell != null) {
//            leftRookLandingPiece = leftRookLandingCell.getPiece();
//        }

        boolean allLeftPiecesEmpty = true;

        if ((leftMostPiece instanceof Rook)
                && (!leftMostPiece.isHasMoved())
                && !king.isOpponent(leftMostPiece)
        ) {
            for (int col = leftMostCell.getCol() + 1; col < kingCol; col++) {
                BoardCell boardCell = board.getBoardCell(kingRow, col);
                if (boardCell.getPiece() != null) {
                    allLeftPiecesEmpty = false;
                    break;
                }
            }

            if (allLeftPiecesEmpty) {
                BoardCell leftBoardCell1 = board.getBoardCell(kingRow, kingCol - 1);
                boolean leftUnderAttack1 = board.isCellUnderAttack(leftBoardCell1);

                BoardCell leftBoardCell2 = board.getBoardCell(kingRow, kingCol - 2);
                boolean leftUnderAttack2 = board.isCellUnderAttack(leftBoardCell2);

                if (!leftUnderAttack1 && !leftUnderAttack2) {
                    castlingMoves.add(new CastlingMove(sourceCell, leftBoardCell2, leftMostCell, leftBoardCell1));
                }
            }
        }

        return castlingMoves;
    }
}
