package FileIO;

import SudokuCLI.Gameplay.DifficultyLevel;

/***
 * An object based representation of all the data required to resume a saved game of SudokuCLI<br>
 * In this case (so far), Reference board @reference, Active board @board, And number of moves so far @moves
 */
public class SaveData
{
    /***
     * The file name of the save file
     */
    public String boardName;
    /***
     * The 2D array representing the solved version of the board being played
     */
    private int[][] reference;
    /***
     * The 2D array representing the unsolved / in-progress version of the board being played
     */
    private int[][] board;
    /***
     * Keeps track of how many times the player has tried to change a cell (successful or not)
     */
    private int moves;

    private DifficultyLevel difficulty;
    /***
     * The error message (if any) returned when constructing the save file object either new or loaded form file
     */
    private String errorMessage = "";

    /***
     * Basic constructor to instantiate all variables from the get-go
     * @param boardName File name of the board - extension
     * @param reference Reference board 2D array
     * @param board In-progress board 2D array
     * @param moves Number of moves taken so far
     */
    public SaveData(String boardName, int[][] reference, int[][] board, int moves, DifficultyLevel difficulty)
    {
        this.boardName = boardName;
        this.reference = reference;
        this.board = board;
        this.moves = moves;
        this.difficulty = difficulty;
    }

    /***
     * Standard getter for {@link #reference}
     * @return {@link #reference}
     */
    public int[][] getReference()
    {
        return reference;
    }

    /***
     * Standard setter for the {@link #reference}
     * @param reference {2D array to replace current reference board}
     */
    public void setReference(int[][] reference)
    {
        this.reference = reference;
    }

    /***
     * Standard getter for {@link #board}
     * @return {@link #board}
     */
    public int[][] getBoard()
    {
        return board;
    }

    /***
     * Standard setter for {@link #board}
     * @param board 2D array to replace the current in-progress board
     */
    public void setBoard(int[][] board)
    {
        this.board = board;
    }

    /***
     * Standard getter for {@link #moves}
     * @return {@link #moves}
     */
    public int getMoves()
    {
        return moves;
    }

    /***
     * Standard setter for {@link #moves}
     * @param moves Integer to replace current number of user moves
     */
    public void setMoves(int moves)
    {
        this.moves = moves;
    }

    public void addMove()
    {
        this.moves++;
    }

    public void setDifficulty(DifficultyLevel difficulty)
    {
        this.difficulty = difficulty;
    }

    public DifficultyLevel getDifficulty()
    {
        return difficulty;
    }

    /***
     * Standard getter for {@link #errorMessage}
     * @return {@link #errorMessage}
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /***
     * Standard setter for {@link #errorMessage}
     * @param errorMessage String to replace the current (if any) error message.
     */
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void setCellValue(int row, int col, int value)
    {
        this.board[row][col] = value;
    }

    /***
     * Allows easy saving of the current SaveData instance
     */
    public void save()
    {
        IOTools.saveFile(this);
    }

    public boolean boardComplete()
    {
        return (this.reference == this.board);
    }
}
