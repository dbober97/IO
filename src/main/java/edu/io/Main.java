package edu.io;
import edu.io.player.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.join(new Player());
        game.start();
    }
}
