package io.github.kapilchoudhary.chessgame.strategy.piecemovement;

public class PieceMovementStrategies {
    public static final PieceMovementStrategy KING = new KingMovement();
    public static final PieceMovementStrategy QUEEN = new QueenMovement();
    public static final PieceMovementStrategy BISHOP = new BishopMovement();
    public static final PieceMovementStrategy KNIGHT = new KnightMovement();
    public static final PieceMovementStrategy ROOK = new RookMovement();
    public static final PieceMovementStrategy PAWN = new PawnMovement();
}
