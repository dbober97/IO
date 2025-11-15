package edu.io;
import edu.io.token.PlayerToken;
import edu.io.token.Token;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gold Rush");
        Scanner scanner = new Scanner(System.in);
        Board b = new Board();
        PlayerToken p = new PlayerToken(b);
        b.display();
        boolean end = false;
        while(!end)
        {
            try {
                System.out.println("Go: \n U - up \n D - down \n L - left \n R - right \n N - don't move \n E - end \n");
                char c = scanner.next().charAt(0);
                switch (c) {
                    case 'U': {
                        p.move(PlayerToken.Move.UP);
                        b.display();
                        break;
                    }
                    case 'D': {
                        p.move(PlayerToken.Move.DOWN);
                        b.display();
                        break;
                    }
                    case 'L': {
                        p.move(PlayerToken.Move.LEFT);
                        b.display();
                        break;
                    }
                    case 'R': {
                        p.move(PlayerToken.Move.RIGHT);
                        b.display();
                        break;
                    }
                    case 'N': {
                        p.move(PlayerToken.Move.NONE);
                        b.display();
                        break;
                    }
                    case 'E': {
                        end = true;
                        System.out.println("***End of the game***");
                        break;
                    }
                    default:
                        break;

                }

            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Cannot move outside the board, try again!");
            }

        }
    }
}
