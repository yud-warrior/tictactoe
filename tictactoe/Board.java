package tictactoe;


public class Board {
    private final static int SIZE = 3;
    private Cell board[][] = new Cell[SIZE][SIZE];

    public Board() {
        init();
    }

    private void init() {
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = Cell.EMPTY;
    }

    public int size() {
        return SIZE;
    }

    public Cell getCell(int row, int col) throws WrongCoordsException {
        if(!isValidCoords(row, col))
            throw new WrongCoordsException(wrongCoordsMessage());
        return getCellUnsafe(row, col);
    }

    public Cell getCellUnsafe(int row, int col) {
        return board[row][col];
    }

    public void setCell(
        int row, 
        int col, 
        Cell typCell
    ) throws WrongCoordsException {
        if(!isValidCoords(row, col))
            throw new WrongCoordsException(wrongCoordsMessage());
        setCellUnsafe(row, col, typCell);
    }

    private void setCellUnsafe(int row, int col, Cell typCell) {
        board[row][col] = typCell;
    }

    private String wrongCoordsMessage() {
        return "Both the coordinates must be between 0 and " +
                Integer.toString(SIZE);
    }

    public static boolean isValidCoords(int x, int y) {
        return (x >= 0 && x < SIZE && y >= 0 && y < SIZE);
    }
}
