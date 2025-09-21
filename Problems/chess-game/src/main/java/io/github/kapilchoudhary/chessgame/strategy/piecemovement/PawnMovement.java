package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

import io.github.kapilchoudhary.chessgame.enums.Direction;
import io.github.kapilchoudhary.chessgame.model.Board;
import io.github.kapilchoudhary.chessgame.model.BoardCell;
import io.github.kapilchoudhary.chessgame.model.move.EnPassantMove;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.Pawn;
import io.github.kapilchoudhary.chessgame.model.piece.Piece;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PawnMovement implements PieceMovementStrategy {
    @Override
    public List<Move> getLegalMoves(@NonNull final BoardCell sourceCell, final Move lastMove, @NonNull final Board board) {
        List<Move> legalMoves = new ArrayList<>();

        Piece pawn = sourceCell.getPiece();
        if (pawn == null) {
            return legalMoves;
        }

        for (Direction direction: Direction.getPawnDirections(pawn.getPieceType())) {
            legalMoves.addAll(getStraightLegalMoves(sourceCell, direction, board));
            legalMoves.addAll(getDiagonalLegalMoves(sourceCell, direction, lastMove, board));
        }

        return legalMoves;
    }

    @Override
    public List<BoardCell> getAttackCells(@NonNull final BoardCell sourceCell, @NonNull final Board board) {
        List<BoardCell> attackCells = new ArrayList<>();

        Piece pawn = sourceCell.getPiece();
        if (pawn == null) {
            return attackCells;
        }

        for (Direction direction: Direction.getPawnDirections(pawn.getPieceType())) {
            if ((direction == Direction.N) || (direction == Direction.S)) {
                continue;
            }

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

    private List<Move> getStraightLegalMoves(@NonNull final BoardCell sourceCell, @NonNull final Direction direction, @NonNull final Board board) {
//        Board board = Board.getBoardInstance();
        List<Move> legalMoves = new ArrayList<>();

        if ((direction != Direction.N) && (direction != Direction.S)) {
            return legalMoves;
        }

        Piece pawn = sourceCell.getPiece();
        if (pawn == null) {
            return legalMoves;
        }

        int nr = sourceCell.getRow() + direction.getDeltaRow();
        int nc = sourceCell.getCol() + direction.getDeltaCol();

        BoardCell targetCell = board.getBoardCell(nr, nc);

        if ((targetCell == null) || (targetCell.getPiece() != null) || board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
            return legalMoves;
        }

        legalMoves.add(new Move(sourceCell, targetCell));

        if (!pawn.isHasMoved()) {
            nr = targetCell.getRow() + direction.getDeltaRow();
            nc = targetCell.getCol() + direction.getDeltaCol();

            BoardCell twoStepTargetCell = board.getBoardCell(nr, nc);

            if ((twoStepTargetCell == null) || (twoStepTargetCell.getPiece() != null) || board.wouldLeaveKingInCheck(sourceCell, twoStepTargetCell)) {
                return legalMoves;
            }

            legalMoves.add(new Move(sourceCell, twoStepTargetCell));
        }

        return legalMoves;
    }

    private List<Move> getDiagonalLegalMoves(@NonNull final BoardCell sourceCell, @NonNull final Direction direction, final Move lastMove, @NonNull final Board board) {
//        Board board = Board.getBoardInstance();
        List<Move> legalMoves = new ArrayList<>();

        if ((direction != Direction.NW) && (direction != Direction.NE) && (direction != Direction.SW) && (direction != Direction.SE)) {
            return legalMoves;
        }

        Piece pawn = sourceCell.getPiece();
        if (pawn == null) {
            return legalMoves;
        }

        int nr = sourceCell.getRow() + direction.getDeltaRow();
        int nc = sourceCell.getCol() + direction.getDeltaCol();

        BoardCell targetCell = board.getBoardCell(nr, nc);

        if (targetCell == null) {
            return legalMoves;
        }

        if (targetCell.getPiece() != null) {
            if (pawn.isOpponent(targetCell.getPiece()) && !board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
                legalMoves.add(new Move(sourceCell, targetCell));
            }
        } else {
            BoardCell enPassantCapturedCell = getEnPassantCapturedCell(sourceCell, lastMove);
            if (enPassantCapturedCell != null) {
                Move enPassantMove = new EnPassantMove(sourceCell, targetCell, enPassantCapturedCell);

                Piece enPassantCapturedPiece = enPassantCapturedCell.getPiece();
                enPassantCapturedCell.setPiece(null);

                if (!board.wouldLeaveKingInCheck(sourceCell, targetCell)) {
                    legalMoves.add(enPassantMove);
                }

                enPassantCapturedCell.setPiece(enPassantCapturedPiece);
            }
        }

        return legalMoves;
    }

    private BoardCell getEnPassantCapturedCell(BoardCell sourceCell, Move lastMove) {
        if (lastMove == null) {
            return null;
        }

        Piece movedPiece = lastMove.getTargetCell().getPiece();
        if (!(movedPiece instanceof Pawn)) {
            return null;
        }

        // Check if the last move was a 2-step pawn move
        int rowDiff = Math.abs(lastMove.getSourceCell().getRow() - lastMove.getTargetCell().getRow());
        if (rowDiff != 2) {
            return null;
        }

        // En passant only works if our pawn is next to that pawn
        if (sourceCell.getRow() == lastMove.getTargetCell().getRow() &&
                Math.abs(sourceCell.getCol() - lastMove.getTargetCell().getCol()) == 1) {
            return lastMove.getTargetCell();
        }

        return null;
    }
}
