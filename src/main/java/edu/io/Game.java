package edu.io;

import edu.io.player.NoTool;
import edu.io.player.Player;
import edu.io.token.*;

import java.util.Scanner;

public class Game {

    private final Board board;
    private Player player;
    private boolean showInfo = true;

    Scanner scanner = new Scanner(System.in);

    public Board board()
    {
        return board;
    }

    public Game()
    {
        board = new Board();
        board.placeToken(0,0, new GoldToken(10));
        board.placeToken(0,1, new GoldToken(8));
        board.placeToken(0,2, new GoldToken(10));
        board.placeToken(0,3, new GoldToken(10));
        board.placeToken(0,4, new GoldToken(10));
        board.placeToken(5,3, new GoldToken(20));
        board.placeToken(2,4, new GoldToken(40));
        board.placeToken(6,4, new GoldToken(60));
        board.placeToken(6,5, new GoldToken(4));
        board.placeToken(6,6, new GoldToken(12));
        board.placeToken(6,7, new GoldToken(16));
        board.placeToken(7,0, new PyriteToken());
        board.placeToken(2,3, new PickaxeToken());
        board.placeToken(7,7, new PickaxeToken());
        board.placeToken(4,4, new AnvilToken());
        board.placeToken(0,7, new SluiceboxToken());
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
                System.out.println("Go: \nU - up  D - down  L - left  R - right  N - don't move  E - end \nS - show info \nH - hide info\n");
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
                    case 'S': {
                        showInfo = true;
                        break;
                    }
                    case 'H': {
                        showInfo = false;
                        break;
                    }
                }
                if(c != 'E' && c != 'e') board.display();
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Cannot move outside the board, try again!");
            }

            if(showInfo) {
                System.out.println("col: " + player.token().pos().col() + " row: " + player.token().pos().row() + "\n");
                System.out.println("gold amount: " + player.gold.amount() + "\n");


                Tool tool;
                System.out.println("COLLECTED TOOLS: \n");
                for (int i = player.shed.toolsAmount() - 1; i >= 0; i--) {
                    tool = player.shed.getToolAt(i);
                    switch (tool) {
                        case PickaxeToken px -> {
                            System.out.println("Pickaxe: \n---gainFactor: " + px.gainFactor() + "\n");
                            System.out.println("---actual durability: " + px.durability() + "\n");
                            System.out.println("---initial durability: " + px.initialDurability() + "\n");
                        }
                        case SluiceboxToken sb -> {
                            System.out.println("Sluicebox: \n---actual gainFactor: " + sb.gainFactor() + "\n");
                            System.out.println("---actual durability: " + sb.durability() + "\n");
                        }
                        default -> {
                        }
                    }
                }
            }

        }
    }
}
