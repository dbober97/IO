package edu.io.token;
import edu.io.Board;
import edu.io.player.Player;

public class PlayerToken extends Token {

    private final Board board;
    private final Player player;
    private int col;
    private int row;

    public Board board()
    {
        return board;
    }

    public Player player()
    {
        return player;
    }

    public enum Move{
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public PlayerToken(Player player, Board board) {
        super(Label.PLAYER_TOKEN_LABEL);
        this.board = board;
        this.player = player;
        this.row = board.getAvailableSquare().row();
        this.col = board.getAvailableSquare().col();
        this.board.placeToken(this.col, this.row, this);
        this.player.assignToken(this);
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
        if(newRow < 0 || newRow >= board.size() || newCol < 0 || newCol >= board.size()) throw new IllegalArgumentException("Cannot move outside the board");

        if(newRow != row || newCol != col) player.interactWithToken(board.peekToken(newCol, newRow));
        board.placeToken(this.col, this.row, new EmptyToken());
        board.placeToken(newCol, newRow, this);
        this.row = newRow;
        this.col = newCol;
    }

    public Board.Coords pos(){
        return new Board.Coords(col, row);
    }

}
