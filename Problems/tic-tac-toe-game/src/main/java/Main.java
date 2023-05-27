import models.MarkO;
import models.MarkX;
import models.Player;
import models.TicTacToeGame;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int boardSize = 3;

        Player player1 = new Player("TOM", new MarkX());
        Player player2 = new Player("JERRY", new MarkO());

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        TicTacToeGame ticTacToeGame = new TicTacToeGame(boardSize, players);

        String result = ticTacToeGame.startGame();
        System.out.println("GAME VERDICT: " + result);
    }
}