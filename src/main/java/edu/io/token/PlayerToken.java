package edu.io.token;
import edu.io.Board;

public class PlayerToken extends Token {

    Board board;
    private int col = 0;
    private int row = 0;

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
            this.board.placeToken(this.col, this.row, this);
    }

    public void move(Move dir)
    {
        switch (dir){
            case NONE:
                break;
            case LEFT:
            {
                if(col > 0) {
                    board.placeToken(col, row, new EmptyToken());
                    col -= 1;
                }
                else throw new IllegalArgumentException("Cannot move outside the board");
                board.placeToken(col, row, this);
                break;
            }
            case RIGHT:
            {
                if(this.col < this.board.size-1){
                    board.placeToken(this.col, this.row, new EmptyToken());
                    this.col += 1;
                }
                else throw new IllegalArgumentException("Cannot move outside the board");
                board.placeToken(col, row, this);
                break;
            }
            case UP:
            {
                if(row > 0){
                    board.placeToken(col, row, new EmptyToken());
                    row -= 1;
                }
                else throw new IllegalArgumentException("Cannot move outside the board");
                board.placeToken(col, row, this);
                break;
            }
            case DOWN:
            {
                if(row < board.size-1){
                    board.placeToken(col, row, new EmptyToken());
                    row += 1;
                }
                else throw new IllegalArgumentException("Cannot move outside the board");
                board.placeToken(col, row, this);
                break;
            }
        }
    }

    public Board.Coords pos(){
        return new Board.Coords(col, row);
    }
}
