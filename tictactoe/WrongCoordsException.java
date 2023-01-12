package tictactoe;

public class WrongCoordinatesException extends Exception {
    public WrongCoordinatesException(String message) {
        super(message);
    }
}
