package edu.io.token;
import edu.io.Board;

public class PlayerToken extends Token {

    Board board;
    private int col;
    private int row;

    public enum Move{
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public PlayerToken(Board board) {
        super(Label.PLAYER_TOKEN_LABEL);
        this.board = board;
        this.row = 0;
        this.col = 0;
        this.board.placeToken(this.col, this.row, this);
     }

    public void move(Move dir)
    {
        int newRow = this.row;
        int newCol = this.col;
        switch (dir){
            case NONE: break;
            case LEFT: {
                newCol--;
                break;
            }
            case RIGHT: {
                newCol++;
                break;
            }
            case UP: {
                newRow--;
                break;
            }
            case DOWN: {
                newRow++;
                break;
            }
        }
        if(newRow < 0 || newRow >= board.size || newCol < 0 || newCol >= board.size()) throw new IllegalArgumentException("Cannot move outside the board");
        board.placeToken(this.col, this.row, new EmptyToken());
        board.placeToken(newCol, newRow, this);
        this.row = newRow;
        this.col = newCol;
    }

    public Board.Coords pos(){
        return new Board.Coords(col, row);
    }
}
