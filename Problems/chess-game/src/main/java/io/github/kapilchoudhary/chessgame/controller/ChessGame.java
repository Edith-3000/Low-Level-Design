package io.github.kapilchoudhary.chessgame.controller;

import io.github.kapilchoudhary.chessgame.model.move.Move;
import io.github.kapilchoudhary.chessgame.model.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame {
    private final List<Move> moves;
    private boolean running;
    @Getter @Setter private Player playerA;
    @Getter @Setter private Player playerB;
    private Player currentPlayer;

    public ChessGame() {
        this.moves = new ArrayList<>();
        this.running = true;
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    public void start() {
        while (running) {

        }
    }
}
