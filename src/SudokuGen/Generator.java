package SudokuGen;

import SudokuCLI.SudokuTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * Contains all methods required to generate a completed and valid Sudoku board in the form of a 2D array
 */
public class Generator
{
    /***
     * The uninitialised {@code ArrayList<Integer>} that contains each cells potential values for testing
     */
    private ArrayList<List<Integer>> cellValues;
    /***
     * The boolean to keep track of whether or not a valid board has been produced by {@link #generate()}
     */
    private boolean boardFinished = false;

    /***
     * Iterates 81 times to produce a full list of size 81 {@code List<Integer>} elements with randomised order using
     * {@link SudokuTools#shuffledNine()}.
     */
    private void populateCellValues()
    {
        for (int i = 0; i < 81; i++)
        {
            cellValues.add(SudokuTools.shuffledNine());
        }
    }

    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a row in the board for a given row to determine if there is any duplicity.
     * @param row The row in the Sudoku grid that needs to be checked for the specified value
     * @param value The value that has to be searched for in the row for duplicity
     * @return A true/false for whether or not the value exists in the given row
     */
    private boolean rowValid(int row, int value, int[][] board)
    {
        for (int i = 0; i < board[row].length; i++)
        {
            if (board[row][i] == value)
            {
                return false;
            }
        }

        return true;
    }

    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a column (col) in the board for a given value to determine if there is any duplicity.
     * @param col The column (col) in the Sudoku grid that needs to be checked for the specified value
     * @param value The value that has to be searched for in the column (col) for duplicity
     * @return A true/false for whether or not the value exists in the given column (col)
     */
    private boolean columnValid(int col, int value, int[][] board)
    {
        for (int i = 0; i < board.length; i++)
        {
            if (board[i][col] == value)
            {
                return false;
            }
        }

        return true;
    }

    /***
     * Used to find a sub-row and sub-column position of a 3 x 3 area of cells in a Sudoku board grid,
     * based on the row and col position of an individual cell.
     * @param row The row of the individual cell inside one of 9 sub-grid.
     * @param col The column of the individual cell inside one of 9 sub-grid.
     * @return An int[] containing sub-row ([0]) and sub-column ([1]) position of the individual cell being searched for.
     */
    private int[] findSubSection(int row, int col)
    {
        int[] subSection = new int[2];

        if (row >= 3 && row < 6)
        {
            subSection[0] = 1;
        }
        else if (row >= 6 && row < 9)
        {
            subSection[0] = 2;
        }

        if (col >= 3 && col < 6)
        {
            subSection[1] = 1;
        }
        else if (col >= 6 && col < 9)
        {
            subSection[1] = 2;
        }

        return subSection;
    }

    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a 3 x 3 sub-section in the board for a given value to determine if there is any duplicity.
     * @param row The row in the Sudoku board that contains the cell in question
     * @param col The column in the Sudoku board that contains the cell in question
     * @param value The integer value (from 1 to 9 inclusive) of the cell in question
     * @return A true/false for whether or not the value exists in its subsection
     * (determined by {@link #findSubSection(int, int)}).
     */
    private boolean subsectionValid(int row, int col, int value, int[][] board)
    {
        int[] subSection = findSubSection(row, col);

        for (int i = (subSection[0] * 3); i < ((subSection[0] * 3) + 3); i++)
        {
            for (int j = (subSection[1] * 3); j < ((subSection[1] * 3) + 3); j++)
            {
                if (value == board[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    /***
     * Method to combine the boolean outputs of the three validation methods ({@link #rowValid(int, int, int[][])},
     * {@link #columnValid(int, int, int[][])}, {@link #subsectionValid(int, int, int, int[][])}) that define the rules of a Sudoku board.
     * @param row The row in the Sudoku board that contains the cell in question
     * @param col The column in the Sudoku board that contains the cell in question
     * @param value The integer value (from 1 to 9 inclusive) of the cell in question
     * @return A true/false depending on the combined outcome of the three methods mentioned in the description.
     */
    private boolean cellValid(int row, int col, int value, int[][] board)
    {
        return rowValid(row, value, board) && columnValid(col, value, board) && subsectionValid(row, col, value, board);
    }

    /***
     * Gets the available values yet to be tested for a specified cell
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     * @return The List that contains the specified cell's remaining possible values
     */
    private List<Integer> getCellValues(int row, int col)
    {
        return this.cellValues.get((row * 9) + col);
    }

    /***
     * Updates the available values yet to be tested for a specified cell
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     * @param list The {@code List<Integer>} that is to replace the old one
     */
    private void setCellValues(int row, int col, List<Integer> list)
    {
        int index = (row * 9) + col;
        this.cellValues.set(index, list);
    }

    /***
     * Triggered when all values possible in a cell have been used - calls the {@link #solveCell(int[][], int, int, boolean)}
     * method on the previous cell and passes the firstTry bool as false to indicate the cell is being visited
     * at least once more.
     * The opposite to {@link #nextCell(int row, int col, int[][])}.
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     */
    private void backtrack(int row, int col, int[][] board)
    {
        if (row == 0)
        {
            if (col == 0)
            {
                solveCell(board, 0, 0, false);
            }
            else
            {
                solveCell(board, row, col - 1, false);
            }
        }
        else
        {
            if (col == 0)
            {
                solveCell(board, row - 1, 8, false);
            }
            else
            {
                solveCell(board, row, col - 1, false);
            }
        }
    }

    /***
     * Triggered when a legal value is found for a cell on the board. Calls the {@link #solveCell(int[][], int, int, boolean)}
     * method on the next cell and passes the firstTry boolean as true to indicate the cell is being altered for the
     * first time.
     * The opposite to {@link #backtrack(int row, int col, int[][] board)}.
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     */
    private void nextCell(int row, int col, int[][] board)
    {
        if (row == 8)
        {
            if (col != 8)
            {
                solveCell(board, row, col + 1, true);
            }
            else
            {
                solveCell(board, 8, 8, true);
            }
        }
        else
        {
            if (col == 8)
            {
                solveCell(board, row + 1, 0, true);
            }
            else
            {
                solveCell(board, row, col + 1, true);
            }
        }
    }

    /***
     * A simple check to indicate if the cell currently being altered is the last cell on the board or not
     * @param row The current row that that cell is part of
     * @param col The current column that the cell is part of
     * @return true / false depending on whether the cell being altered is at position 81 (final cell)
     */
    private boolean finalCell(int row, int col)
    {
        return ((row * 9) + col) == 81;
    }

    /***
     * The primary method of the SudokuGen class. This method recursively checks all numbers 1-9 inclusive
     * (stored in a LinkedList and randomised with {@link java.util.Collections#shuffle(List, Random)} until it
     * finds a valid number based on sudoku's row/column/3x3 subsection rules. If a valid number is found, it places
     * it in the current cell and continues with {@link #nextCell(int, int, int[][])} - if not, it recreates the cells number
     * list (newly randomised) and tries the same process on the previous cell. During this, any time a number is
     * tested for the cell (valid or invalid) it is removed from that cells number list until triggering a
     * {@link #backtrack(int, int, int[][])} when empty.
     *
     * @param row The row containing the cell to be tested
     * @param col The column containing the cell to be tested
     * @param firstTry Whether or not the cell to be tested is being tested for the first time (tru) or not (false)
     */
    private void solveCell(int[][] board, int row, int col, boolean firstTry)
    {
        if (!firstTry)
        {
            board[row][col] = 0;
        }
        if (board[row][col] == 0 && !finalCell(row, col) && !this.boardFinished)
        {

            List<Integer> cellNumbers = getCellValues(row, col);
            boolean cellSet = false;

            while (!cellNumbers.isEmpty())
            {

                int cellValue = cellNumbers.get(cellNumbers.size() - 1);
                boolean legal = cellValid(row, col, cellValue, board);

                if (legal)
                {
                    board[row][col] = cellValue;
                    cellNumbers.remove(cellNumbers.size() - 1);
                    cellSet = true;
                    nextCell(row, col, board);
                    break;
                }
                else
                {
                    cellNumbers.remove(cellNumbers.size() - 1);
                }
            }


            if (!cellSet)
            {
                setCellValues(row, col, SudokuTools.shuffledNine());
                board[row][col] = 0;
                backtrack(row, col, board);
            }
        }
        else
        {
            this.boardFinished = true;
        }
    }

    /***
     * Method used to begin the sudoku board generation process at board position {@code row = 0} {@code column = 0}.
     * @return The generated 2D array representing the board
     */
    public int[][] generate()
    {
        int[][] board = new int[9][9];
        this.populateCellValues();
        this.solveCell(board, 0,0, true);
        return board;
    }

    private static int COUNTER;

    public int[][] unSolver(int[][] board){
        int[][] completeBoard = board.clone();
        int chances = 40; // THISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS changes with difficulty
        COUNTER = 1;
        Random rand = new Random();
        while(chances > 0){
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            while(board[row][col] == 0){
                row = rand.nextInt(9);
                col = rand.nextInt(9);
            }
            int[][] boardBackup = board.clone();
            int valBackup = board[row][col];
            board[row][col] = 0;
            solveBoard(boardBackup);
            if(COUNTER != 1){
                board[row][col] = valBackup;
                chances--;
            }
        }
        return board;
    }

    public boolean solveBoard(int[][] board){
        COUNTER = 0;
        Random rand = new Random();
        for(int i = 0; i < 9; i++){
            for(int j = 0; i<9; j++){
                if(board[i][j] == 0){
                    for(int value = 1; value < 10; value++){
                        if(cellValid(i, j, value, board)){
                            board[i][j] = value;
                            if(boardCheck(board)){
                                COUNTER += 1;
                                break;
                            }
                            else{
                                if(solveBoard(board)){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean boardCheck(int[][] board){
        for(int row = 1; row<10; row++){
            for(int col = 1; col<10; col++){
                if(board[row][col]==0){
                    return false;
                }
            }
        }
        return true;
    }
}
