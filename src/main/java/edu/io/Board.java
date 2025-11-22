package edu.io;

import edu.io.token.EmptyToken;
import edu.io.token.Token;

public class Board {

    private final int size = 8;
    private final Token[][] grid = new Token[size][size];
    public record Coords(int col, int row) {}

    public int size() {
        return size;
    }

    public Board()
    {
        this.clean();
    }

    public void clean()
    {
        EmptyToken t = new EmptyToken();
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++) grid[j][i] = t;
        }
    }

    public void placeToken(int col, int row, Token t)
    {
        grid[row][col] = t;
    }

    public Token square(int col, int row)
    {
        return grid[row][col];
    }

    public void display()
    {
        {
            for(int i = 0; i < size; i++)
            {
                for(int j = 0; j < size; j++) System.out.print(grid[i][j].label());
                System.out.println("\n");
            }
        }
    }

    public Token peekToken(int col, int row)
    {
        return grid[row][col];
    }

    public Coords getAvailableSquare()
    {
        //funkcja zwraca wspolrzedne pierwszego nie zajetego pola na planszy
        for(int row = 0; row < size; row++)
        {
            for(int col = 0; col < size; col++)
            {
                if (grid[row][col] instanceof EmptyToken) return new Coords(col, row);
            }
        }
        throw new IllegalStateException("Board is full!");
    }
}
