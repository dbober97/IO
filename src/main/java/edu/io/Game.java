package edu.io;

import edu.io.player.Player;
import edu.io.token.*;

import java.util.Scanner;

public class Game {

    private final Board board;
    private Player player;

    Scanner scanner = new Scanner(System.in);

    public Board board()
    {
        return board;
    }

    public Game()
    {
        board = new Board();
        board.placeToken(0,0, new GoldToken(10));
        board.placeToken(5,3, new GoldToken(20));
        board.placeToken(2,4, new GoldToken(20));
        board.placeToken(7,0, new PyriteToken());
        board.placeToken(2,3, new PickaxeToken());
        board.placeToken(4,4, new AnvilToken());
    }

    public void join(Player player)
    {
        this.player = player;
        PlayerToken p = new PlayerToken(player, board); //tworzymy pionek reprezentujacy gracza (i ustawiamy na pierwszym wolnym miejscu)
        this.player.assignToken(p); //przypisujemy graczowi nowo utworzony pionek

    }

    public void start()
    {
        System.out.println("Gold Rush");

        board.display();

        boolean end = false;
        while(!end)
        {
            try {
                System.out.println("Go: \n U - up \n D - down \n L - left \n R - right \n N - don't move \n E - end \n");
                char c = scanner.next().toUpperCase().charAt(0);
                switch (c) {
                    case 'U': {
                        player.token().move(PlayerToken.Move.UP);
                        break;
                    }
                    case 'D': {
                        player.token().move(PlayerToken.Move.DOWN);
                        break;
                    }
                    case 'L': {
                        player.token().move(PlayerToken.Move.LEFT);
                        break;
                    }
                    case 'R': {
                        player.token().move(PlayerToken.Move.RIGHT);
                        break;
                    }
                    case 'N': {
                        player.token().move(PlayerToken.Move.NONE);
                        break;
                    }
                    case 'E': {
                        end = true;
                        System.out.println("***End of the game***");
                        break;
                    }
                }
                if(c != 'E' && c != 'e') board.display();
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Cannot move outside the board, try again!");
            }
            System.out.println("col: " + player.token().pos().col() + " row: " + player.token().pos().row() + "\n");
            System.out.println("gold amount: " + player.gold.amount()+ "\n");
        }
    }
}
