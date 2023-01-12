package tictactoe;

public class Game {
    private Board board;
    private Player turn;
    private GameState state;

    public Game() {
        board = new Board();
        turn = Player.X;
        state = GameState.UNFINISHED;
    }

    public Player getTurn() {
        return turn;
    }

    public GameState getState() {
        return state;
    }

    public void makeMove(Move move) throws WrongMoveException {
        throwWrongMoveExceptionIfWrong(move);
        setCellBy(move);
        changeTurn();
        changeState();
    }

    private void throwWrongMoveExceptionIfWrong(Move move) throws WrongMoveException {
        Cell typCell;
        try { 
            typCell = board.getCell(move.row, move.col);
        } catch(WrongCoordinatesException ex) {
            throw new WrongMoveException(ex.getMessage());
        }

        if(typCell != Cell.EMPTY)
            throw new WrongMoveException(moveToNotEmptyCellMessage());
    }

    private String moveToNotEmptyCellMessage() {
        return "Cell must be empty";
    }

    private void setCellBy(Move move) throws WrongMoveException {
        try {
            board.setCell(move.row, move.col, turnTypCell());
        } catch(WrongCoordinatesException ex) {
            throw new WrongMoveException(ex.getMessage());
        }
    }

    private Cell turnTypCell() {
        if(turn == Player.X)
            return Cell.X;
        return Cell.O;
    }

    private void changeTurn() {
        if(turn == Player.X)
            turn = Player.O;
        else
            turn = Player.X;
    }

    private void changeState() {
        state = calcState();
    }

    private GameState calcState() {
        GameState gState = getStateFromAllLines();
        if(gState == GameState.UNFINISHED && thereIsNoEmptyCell())
                gState = GameState.TIE;
        return gState;
    }

    private boolean thereIsNoEmptyCell() {
        for(int i = 0; i < board.size(); i++)
            for(int j = 0; j < board.size(); j++)
                if(getCellSafe(i, j) == Cell.EMPTY)
                    return false;
        return true;
    }

    private GameState getStateFromAllLines() {
        GameState lineStates[] = {
            getStateByRows(),
            getStateByCols(),
            getStateByMainDiagonal(),
            getStateByNotMainDiagonal()
        };
        
        for(GameState gState: lineStates) 
            if(gState != GameState.UNFINISHED)
                return gState;
        return GameState.UNFINISHED;
    }

    private GameState getStateByRows() {
        for(int i = 0; i < board.size(); i++) {
            if(getStateByRow(i) != GameState.UNFINISHED)
                return getStateByRow(i);
        }
        return GameState.UNFINISHED;
    }

    private GameState getStateByCols() {
        for(int i = 0; i < board.size(); i++) {
            if(getStateByCol(i) != GameState.UNFINISHED)
                return getStateByCol(i);
        }
        return GameState.UNFINISHED;
    }

    private GameState getStateByRow(int row) {
        Cell firstCellInLine = getCellSafe(row, 0);

        for(int i = 1; i < board.size(); i++) {
            if(getCellSafe(row, i) != firstCellInLine)
                return GameState.UNFINISHED;
        }
        return getStateByLineTypCell(firstCellInLine);
    }

    private GameState getStateByCol(int col) {
        Cell firstCellInLine = getCellSafe(0, col);

        for(int i = 1; i < board.size(); i++) {
            if(getCellSafe(i, col) != firstCellInLine)
                return GameState.UNFINISHED;
        }
        return getStateByLineTypCell(firstCellInLine);
    }

    private GameState getStateByMainDiagonal() {
        Cell firstCellInLine = getCellSafe(0, 0);

        for(int i = 1; i < board.size(); i++) {
            if(getCellSafe(i, i) != firstCellInLine)
                return GameState.UNFINISHED;
        }
        return getStateByLineTypCell(firstCellInLine);
    }

    private GameState getStateByNotMainDiagonal() {
        Cell firstCellInLine = getCellSafe(board.size() - 1, 0);

        for(int i = 1; i < board.size(); i++) {
            if(getCellSafe(board.size() - 1 - i, i) != firstCellInLine)
                return GameState.UNFINISHED;
        }
        return getStateByLineTypCell(firstCellInLine);
    }

    private Cell getCellSafe(int row, int col) {
        Cell cell = Cell.EMPTY;
        try {
            cell = board.getCell(row, col);
        } catch (WrongCoordinatesException ex) {
            //it will never happenned in this method
        }
        return cell;
    }

    private GameState getStateByLineTypCell(Cell typCell) {
        if(typCell == Cell.O)
            return GameState.O_WON;
        
        if(typCell == Cell.X)
            return GameState.X_WON;
        
        return GameState.UNFINISHED;
    }
}
