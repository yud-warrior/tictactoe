package tictactoe;

public class WrongMoveException extends Exception{
    public WrongMoveException(String message) {
        super(message);
    }
}
