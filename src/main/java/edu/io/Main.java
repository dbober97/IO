package edu.io;
import edu.io.token.PlayerToken;
import edu.io.token.Token;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.join(new Player());
        game.start();
    }
}
