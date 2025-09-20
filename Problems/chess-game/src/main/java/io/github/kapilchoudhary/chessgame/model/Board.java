package io.github.kapilchoudhary.chessgame.model;

import io.github.kapilchoudhary.chessgame.constants.AppConstants;
import io.github.kapilchoudhary.chessgame.enums.CellType;
import io.github.kapilchoudhary.chessgame.enums.PieceType;
import io.github.kapilchoudhary.chessgame.model.move.CastlingMove;
import io.github.kapilchoudhary.chessgame.model.move.EnPassantMove;
import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.piece.*;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    @Getter private final int rows;
    @Getter private final int columns;
    private static Board boardInstance;
    private final BoardCell[][] boardCells;
    private final List<Piece> pieces;

    private static final Map<Class<? extends Piece>, String[]> pieceSymbols = Map.of(
            King.class, new String[]{"♔", "♚"},
            Queen.class, new String[]{"♕", "♛"},
            Rook.class, new String[]{"♖", "♜"},
            Bishop.class, new String[]{"♗", "♝"},
            Knight.class, new String[]{"♘", "♞"},
            Pawn.class, new String[]{"♙", "♟"}
    );

    private Board(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        this.boardCells = new BoardCell[rows][columns];
        this.pieces = new ArrayList<>();
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

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Piece piece = boardCells[i][j].getPiece();
                if (piece != null) {
                    pieces.add(boardCells[i][j].getPiece());
                }
            }
        }
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

    public void applyMove(@NonNull final Move move, @NonNull final Player currentPlayer) {
        if (move instanceof CastlingMove) {
            BoardCell rookFrom = ((CastlingMove) move).getRookFrom();
            BoardCell rookTo = ((CastlingMove) move).getRookTo();
            BoardCell kingFrom = move.getSourceCell();
            BoardCell kingTo = move.getTargetCell();

            rookFrom.getPiece().setHasMoved(true);
            kingFrom.getPiece().setHasMoved(true);

            rookTo.setPiece(rookFrom.getPiece());
            rookFrom.setPiece(null);

            kingTo.setPiece(kingFrom.getPiece());
            kingFrom.setPiece(null);
        } else if (move instanceof EnPassantMove) {
            BoardCell toBeCapturedPawnCell = ((EnPassantMove) move).getCapturedPawnCell();

            BoardCell sourceCell = move.getSourceCell();
            BoardCell targetCell = move.getTargetCell();

            sourceCell.getPiece().setHasMoved(true); // Don't really need it by the way
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.setPiece(null);

            currentPlayer.addCapturedPiece(toBeCapturedPawnCell.getPiece());
            toBeCapturedPawnCell.getPiece().capture();
            toBeCapturedPawnCell.setPiece(null);
        } else {
            BoardCell sourceCell = move.getSourceCell();
            BoardCell targetCell = move.getTargetCell();

            sourceCell.getPiece().setHasMoved(true);
            currentPlayer.addCapturedPiece(targetCell.getPiece());
            targetCell.getPiece().capture();
            targetCell.setPiece(sourceCell.getPiece());
            sourceCell.setPiece(null);
        }
    }

    public List<Move> getAllLegalMoves(@NonNull final PieceType pieceType, final Move lastMove) {
        List<Move> allLegalMoves = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                BoardCell cell = boardCells[row][col];
                Piece piece = cell.getPiece();

                if ((piece != null) && (piece.getPieceType() == pieceType)) {
                    allLegalMoves.addAll(piece.getPieceMovementStrategy().getLegalMoves(cell, lastMove, boardInstance));
                }
            }
        }

        return allLegalMoves;
    }

    public void displayBoard() {
        System.out.println();

        System.out.print("    ");
        for (int col = 0; col < columns; col++) {
            System.out.print(" " + (char) ('a' + col) + "   ");
        }
        System.out.println();

        // Top border
        System.out.print("   ┌");
        for (int col = 0; col < columns - 1; col++) {
            System.out.print("───┬");
        }
        System.out.println("───┐");

        for (int row = 0; row < rows; row++) {
            // Row label on left
            System.out.print((rows - row) + "  │");

            for (int col = 0; col < columns; col++) {
                BoardCell cell = boardCells[row][col];
                boolean isWhiteSquare = (row + col) % 2 == 0;

                String bgColor = isWhiteSquare ? "\u001B[47m" : "\u001B[40m";
                String fgColor = "\u001B[30m";
                String reset = "\u001B[0m";

                String symbol = " ";
                if (cell.getPiece() != null) {
                    symbol = getPieceSymbol(cell.getPiece());
                    fgColor = (cell.getPiece().getPieceType() == PieceType.WHITE) ? "\u001B[1;37m" : "\u001B[1;30m";
                }

                System.out.print(bgColor + fgColor + " " + symbol + " " + reset + "│");
            }

            // Row label on right
            System.out.println(" " + (rows - row));

            if (row < rows - 1) {
                System.out.print("   ├");
                for (int col = 0; col < columns - 1; col++) {
                    System.out.print("───┼");
                }
                System.out.println("───┤");
            }
        }

        // Bottom border
        System.out.print("   └");
        for (int col = 0; col < columns - 1; col++) {
            System.out.print("───┴");
        }
        System.out.println("───┘");

        // Column headers again
        System.out.print("    ");
        for (int col = 0; col < columns; col++) {
            System.out.print(" " + (char) ('a' + col) + "   ");
        }
        System.out.println();
    }

    private String getPieceSymbol(Piece piece) {
        String[] symbols = pieceSymbols.get(piece.getClass());
        if (symbols == null) return "?";
        return piece.getPieceType() == PieceType.WHITE ? symbols[0] : symbols[1];
    }
}
