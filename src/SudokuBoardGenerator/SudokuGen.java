package SudokuBoardGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/***
 * todo:
 *  - Do the 3 subgrid pre-fill version of the algorithm
 */

/***
 * Contains all methods required to generate a completed and valid Sudoku board in the form of a 2D array
 */
public class SudokuGen
{
    /***
     * The uninitialised 2D array that represents the Sudoku board
     */
    private int[][] board;
    /***
     * The uninitialised {@code ArrayList<Integer>} that contains each cells potential values for testing
     */
    private ArrayList<List<Integer>> cellValues;
    /***
     * The boolean to keep track of whether or not a valid board has been produced by {@link #generate()}
     */
    private boolean boardFinished = false;

    /***
     * Standard default constructor to automatically generate and populate a Sudoku board into a 2D array when called
     */
    public SudokuGen()
    {
        this.board = new int[9][9];
        this.cellValues =  new ArrayList<>();
        this.generate();
    }

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
    private boolean rowValid(int row, int value)
    {
        for (int i = 0; i < this.board[row].length; i++)
        {
            if (this.board[row][i] == value)
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
    private boolean columnValid(int col, int value)
    {
        for (int i = 0; i < this.board.length; i++)
        {
            if (this.board[i][col] == value)
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
    private boolean subsectionValid(int row, int col, int value)
    {
        int[] subSection = findSubSection(row, col);

        for (int i = (subSection[0] * 3); i < ((subSection[0] * 3) + 3); i++)
        {
            for (int j = (subSection[1] * 3); j < ((subSection[1] * 3) + 3); j++)
            {
                if (value == this.board[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    /***
     * Method to combine the boolean outputs of the three validation methods ({@link #rowValid(int, int)},
     * {@link #columnValid(int, int)}, {@link #subsectionValid(int, int, int)}) that define the rules of a Sudoku board.
     * @param row The row in the Sudoku board that contains the cell in question
     * @param col The column in the Sudoku board that contains the cell in question
     * @param value The integer value (from 1 to 9 inclusive) of the cell in question
     * @return A true/false depending on the combined outcome of the three methods mentioned in the description.
     */
    private boolean cellValid(int row, int col, int value)
    {
        return rowValid(row, value) && columnValid(col, value) && subsectionValid(row, col, value);
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
     * Triggered when all values possible in a cell have been used - calls the {@link #solveCell(int, int, boolean)}
     * method on the previous cell and passes the firstTry bool as false to indicate the cell is being visited
     * at least once more.
     * The opposite to {@link #nextCell(int row, int col)}.
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     */
    private void backtrack(int row, int col)
    {
        if (row == 0)
        {
            if (col == 0)
            {
                solveCell(0,0, false);
            }
            else
            {
                solveCell(row, col - 1, false);
            }
        }
        else
        {
            if (col == 0)
            {
                solveCell(row - 1, 8, false);
            }
            else
            {
                solveCell(row, col - 1, false);
            }
        }
    }

    /***
     * Triggered when a legal value is found for a cell on the board. Calls the {@link #solveCell(int, int, boolean)}
     * method on the next cell and passes the firstTry boolean as true to indicate the cell is being altered for the
     * first time.
     * The opposite to {@link #backtrack(int row, int col)}.
     * @param row The current row that the cell is part of
     * @param col The current column that the cell is part of
     */
    private void nextCell(int row, int col)
    {
        if (row == 8)
        {
            if (col != 8)
            {
                solveCell(row, col + 1, true);
            }
            else
            {
                solveCell(8, 8, true);
            }
        }
        else
        {
            if (col == 8)
            {
                solveCell(row + 1, 0, true);
            }
            else
            {
                solveCell(row, col + 1, true);
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
     * it in the current cell and continues with {@link #nextCell(int, int)} - if not, it recreates the cells number
     * list (newly randomised) and tries the same process on the previous cell. During this, any time a number is
     * tested for the cell (valid or invalid) it is removed from that cells number list until triggering a
     * {@link #backtrack(int, int)} when empty.
     *
     * @param row The row containing the cell to be tested
     * @param col The column containing the cell to be tested
     * @param firstTry Whether or not the cell to be tested is being tested for the first time (tru) or not (false)
     */
    private void solveCell(int row, int col, boolean firstTry)
    {
        if (!firstTry)
        {
            this.board[row][col] = 0;
        }
        if (this.board[row][col] == 0 && !finalCell(row, col) && !this.boardFinished)
        {

            List<Integer> cellNumbers = getCellValues(row, col);
            boolean cellSet = false;

            while (!cellNumbers.isEmpty())
            {

                int cellValue = cellNumbers.get(cellNumbers.size() - 1);
                boolean legal = cellValid(row, col, cellValue);

                if (legal)
                {
                    this.board[row][col] = cellValue;
                    cellNumbers.remove(cellNumbers.size() - 1);
                    cellSet = true;
                    nextCell(row, col);
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
                this.board[row][col] = 0;
                backtrack(row, col);
            }
        }
        else
        {
            this.boardFinished = true;
        }
    }

    /***
     * Method used to begin the sudoku board generation process at board position {@code row = 0} {@code column = 0}.
     */
    private void generate()
    {
        this.populateCellValues();
        this.solveCell(0,0, true);
    }

    /***
     * Getter for the 2D array that represents the sudoku board
     * @return int[r][c] representing the sudoku board where r = the row (y value) and c = column (x value) from
     * top to bottom and left to right respectively
     */
    public int[][] getBoard()
    {
        return this.board;
    }

    /***
     * Improved look to the standard toString method to work with a 2D array
     * @return A multi-line string that displays the Sudoku board in 2d space, each row printed consecutively and
     * formatted to look like so: [n1, n2, n3, ..., n9]
     */
    @Override
    public String toString()
    {
        return "" +
                Arrays.toString(this.board[0]) + System.lineSeparator() +
                Arrays.toString(this.board[1]) + System.lineSeparator() +
                Arrays.toString(this.board[2]) + System.lineSeparator() +
                Arrays.toString(this.board[3]) + System.lineSeparator() +
                Arrays.toString(this.board[4]) + System.lineSeparator() +
                Arrays.toString(this.board[5]) + System.lineSeparator() +
                Arrays.toString(this.board[6]) + System.lineSeparator() +
                Arrays.toString(this.board[7]) + System.lineSeparator() +
                Arrays.toString(this.board[8]);
    }
}
