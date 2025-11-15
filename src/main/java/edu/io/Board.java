package edu.io;

public class Board {

    public int size = 8;
    private final Token[][] grid = new Token[size][size];

    public Board()
    {
        this.clean();
    }

    public void clean()
    {
        Token t = new Token("・");
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
                for(int j = 0; j < size; j++) System.out.print(grid[i][j].label);

            }
        }
    }
}
