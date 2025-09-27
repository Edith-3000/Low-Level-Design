package io.github.kapilchoudhary.chessgame.constants;

public final class AppConstants {
    private AppConstants() {
        throw new AssertionError("Cannot instantiate " + getClass().getSimpleName() + " class");
    }

    // Column constants
    public static final int COL_ZERO  = 0;
    public static final int COL_ONE   = 1;
    public static final int COL_TWO   = 2;
    public static final int COL_THREE = 3;
    public static final int COL_FOUR  = 4;
    public static final int COL_FIVE  = 5;
    public static final int COL_SIX   = 6;
    public static final int COL_SEVEN = 7;

    // Row constants
    public static final int ROW_ZERO  = 0;
    public static final int ROW_ONE   = 1;
    public static final int ROW_TWO   = 2;
    public static final int ROW_THREE = 3;
    public static final int ROW_FOUR  = 4;
    public static final int ROW_FIVE  = 5;
    public static final int ROW_SIX   = 6;
    public static final int ROW_SEVEN = 7;

    public static final int STANDARD_CHESS_ROWS = 8;
    public static final int STANDARD_CHESS_COLUMNS = 8;
}
