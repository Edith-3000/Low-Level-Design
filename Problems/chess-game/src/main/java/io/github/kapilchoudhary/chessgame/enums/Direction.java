package io.github.kapilchoudhary.chessgame.enums;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public enum Direction {
    N(-1, 0),
    S(1, 0),
    E(0, 1),
    W(0, -1),
    NW(-1, -1),
    NE(-1, 1),
    SW(1, -1),
    SE(1, 1),
    KNIGHT_NNE(-2, 1),
    KNIGHT_ENE(-1, 2),
    KNIGHT_ESE(1, 2),
    KNIGHT_SSE(2, 1),
    KNIGHT_SSW(2, -1),
    KNIGHT_WSW(1, -2),
    KNIGHT_WNW(-1, -2),
    KNIGHT_NNW(-2, -1);

    @Getter private final int deltaRow;
    @Getter private final int deltaCol;

    private Direction(final int deltaRow, final int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    public static List<Direction> getKingDirections() {
        return List.of(N, S, E, W, NW, NE, SW, SE);
    }

    public static List<Direction> getQueenDirections() {
        return List.of(N, S, E, W, NW, NE, SW, SE);
    }

    public static List<Direction> getBishopDirections() {
        return List.of(NW, NE, SW, SE);
    }

    public static List<Direction> getKnightDirections() {
        return List.of(KNIGHT_NNE, KNIGHT_ENE, KNIGHT_ESE, KNIGHT_SSE,
                       KNIGHT_SSW, KNIGHT_WSW, KNIGHT_WNW, KNIGHT_NNW);
    }

    public static List<Direction> getRookDirections() {
        return List.of(N, S, E, W);
    }

    public static List<Direction> getPawnDirections(@NonNull final PieceType pieceType) {
        if (pieceType == PieceType.WHITE) {
            return List.of(N, NW, NE);
        }

        return List.of(S, SW, SE);
    }
}
