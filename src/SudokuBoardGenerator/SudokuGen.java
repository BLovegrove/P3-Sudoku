package SudokuBoardGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuGen
{
    private int[][] board = new int[9][9];
    private ArrayList<List<Integer>> cellValues = new ArrayList<>();
    private boolean boardFinished = false;

    /***
     * Iterates 81 times to produce a full list of size 81 {@link List<Integer>} elements with randomised order using
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
     * Method to combine the boolean outputs of three validation methods that define the rules of a Sudoku board.
     * @param row The row in the Sudoku board that contains the cell in question
     * @param col The column in the Sudoku board that contains the cell in question
     * @param value The integer value (from 1 to 9 inclusive) of the cell in question
     * @return A true/false depending on the combined outcome of the three methods {@link #rowValid(int, int)},
     * {@link #columnValid(int, int)}, and {@link #subsectionValid(int, int, int)}.
     */
    private boolean cellValid(int row, int col, int value)
    {
        return rowValid(row, value) && columnValid(col, value) && subsectionValid(row, col, value);
    }

    private List<Integer> getCellValues(int row, int col)
    {
        return this.cellValues.get((row * 9) + col);
    }

    private void setCellValues(int row, int col, List<Integer> list)
    {
        int index = (row * 9) + col;
        this.cellValues.set(index, list);
    }

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

    private boolean finalCell(int row, int col)
    {
        return ((row * 9) + col) == 81;
    }

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
//                    System.out.println("Next cell!");
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
//            System.out.println("Backtracked!");
                this.board[row][col] = 0;
                backtrack(row, col);
            }
        }
        else
        {
            this.boardFinished = true;
        }
    }

    public void generate()
    {
        populateCellValues();
        solveCell(0,0, true);
        System.out.println(this);
    }

    @Override
    public String toString()
    {
        return "" +
                Arrays.toString(this.board[0]) + "\r\n" +
                Arrays.toString(this.board[1]) + "\r\n" +
                Arrays.toString(this.board[2]) + "\r\n" +
                Arrays.toString(this.board[3]) + "\r\n" +
                Arrays.toString(this.board[4]) + "\r\n" +
                Arrays.toString(this.board[5]) + "\r\n" +
                Arrays.toString(this.board[6]) + "\r\n" +
                Arrays.toString(this.board[7]) + "\r\n" +
                Arrays.toString(this.board[8]);
    }
}
