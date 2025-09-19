package io.github.kapilchoudhary.chessgame.model;

import io.github.kapilchoudhary.chessgame.constants.AppConstants;
import io.github.kapilchoudhary.chessgame.enums.CellType;
import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.*;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class Board {
    @Getter private final int rows;
    @Getter private final int columns;
    private static Board boardInstance;
    private final BoardCell[][] boardCells;

    private Board(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        this.boardCells = new BoardCell[rows][columns];
    }

    public static void init(final int rows, final int columns) {
        if (boardInstance != null) {
            throw new IllegalStateException("Board is already initialized.");
        }

        boardInstance = new Board(rows, columns);
    }

    public void defaultBoardSetup() {
        embedBoardCells();
        placeKings();
        placeQueens();
        placeBishops();
        placeKnights();
        placeRooks();
        placePawns();
    }

    public static Board getBoardInstance() {
        if (boardInstance == null) {
            throw new IllegalStateException("Board not initialized, call init() method first.");
        }

        return boardInstance;
    }

    public boolean outOfBoundary(final int row, final int col) {
        return (row < 0) || (row >= rows) ||
                (col < 0) || (col >= columns);
    }

    public BoardCell getBoardCell(final int row, final int col) {
//        if (outOfBoundary(row, col)) {
//            throw new IllegalArgumentException("Provided [row, col] pair: [" + row + ", " + col + "]" + " is out of board boundary.");
//        }

        if (outOfBoundary(row, col)) {
            return null;
        }

        return boardCells[row][col];
    }

    public boolean isCellUnderAttack(@NonNull final BoardCell boardCell, final Move lastMove) {
        Piece piece = boardCell.getPiece();
        if (piece == null) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i == boardCell.getRow()) && (j == boardCell.getCol())) {
                    continue;
                }

                BoardCell currCell = boardCells[i][j];

                if (currCell.getPiece() == null || currCell.getPiece().getPieceType() == piece.getPieceType()) {
                    continue;
                }

                List<Move> legalMoves = piece.getPieceMovementStrategy().getLegalMoves(boardCells[i][j], lastMove, boardInstance);

                for (Move move: legalMoves) {
                    if (move.getTargetCell() == boardCell) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void embedBoardCells() {
        boolean isLightCell = true;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isLightCell) {
                    boardCells[i][j] = new BoardCell(i, j, CellType.LIGHT);
                } else {
                    boardCells[i][j] = new BoardCell(i, j, CellType.DARK);
                }

                isLightCell = !isLightCell;
            }
        }
    }

    private void placeKings() {
        Piece whiteKing = new King(PieceType.WHITE);
        Piece blackKing = new King(PieceType.BLACK);

        boardCells[AppConstants.ROW_SEVEN][AppConstants.COL_FOUR].setPiece(whiteKing);
        boardCells[AppConstants.ROW_ZERO][AppConstants.COL_FOUR].setPiece(blackKing);
    }

    private void placeQueens() {
        Piece whiteQueen = new Queen(PieceType.WHITE);
        Piece blackQueen = new Queen(PieceType.BLACK);

        boardCells[AppConstants.ROW_SEVEN][AppConstants.COL_THREE].setPiece(whiteQueen);
        boardCells[AppConstants.ROW_ZERO][AppConstants.COL_THREE].setPiece(blackQueen);
    }

    private void placeBishops() {
        Piece whiteBishop = new Bishop(PieceType.WHITE);
        Piece blackBishop = new Bishop(PieceType.BLACK);

        boardCells[AppConstants.ROW_SEVEN][AppConstants.COL_TWO].setPiece(whiteBishop);
        boardCells[AppConstants.ROW_ZERO][AppConstants.COL_TWO].setPiece(blackBishop);
    }

    private void placeKnights() {
        Piece whiteKnight = new Knight(PieceType.WHITE);
        Piece blackKnight = new Knight(PieceType.BLACK);

        boardCells[AppConstants.ROW_SEVEN][AppConstants.COL_ONE].setPiece(whiteKnight);
        boardCells[AppConstants.ROW_ZERO][AppConstants.COL_ONE].setPiece(blackKnight);
    }

    private void placeRooks() {
        Piece whiteRook = new Rook(PieceType.WHITE);
        Piece blackRook = new Rook(PieceType.BLACK);

        boardCells[AppConstants.ROW_SEVEN][AppConstants.COL_ZERO].setPiece(whiteRook);
        boardCells[AppConstants.ROW_ZERO][AppConstants.COL_ZERO].setPiece(blackRook);
    }

    private void placePawns() {
        for (int col = 0; col < columns; col++) {
            Piece whitePawn = new Pawn(PieceType.WHITE);
            Piece blackPawn = new Pawn(PieceType.BLACK);

            boardCells[AppConstants.ROW_SIX][col].setPiece(whitePawn);
            boardCells[AppConstants.ROW_ONE][col].setPiece(blackPawn);
        }
    }

    public BoardCell getKingCell(@NonNull final PieceType pieceType) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Piece piece = boardCells[i][j].getPiece();
                if ((piece instanceof King) && (piece.getPieceType() == pieceType)) {
                    return boardCells[i][j];
                }
            }
        }

        return null; // Should not ever happen
    }
}
