package SudokuBoardGenerator;

import java.util.*;

public class ColumnsAndRows
{
    private int[][] board = new int[9][9];

    public ColumnsAndRows()
    {
        populateBoard();
    }

    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a {@code row} in the board for a given {@code value} to determine if there is any duplicity.
     * @param board The 2D Array that represents the Sudoku board numerically.
     * @param row The {@code row} in the Sudoku grid that needs to be checked for the specified value
     * @param value The {@code value} that has to be searched for in the {@code row} for duplicity
     * @return A true/false for whether or not the {@code value} exists in the given {@code row}
     */
    private boolean valueInRow(int[][] board, int row, int value)
    {
        for (int i = 0; i < board[row].length; i++)
        {
            if (board[row][i] == value)
            {
                return true;
            }
        }

        return false;
    }

    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a {@code col(umn)} in the board for a given {@code value} to determine if there is any duplicity.
     * @param board The 2D Array that represents the Sudoku board numerically.
     * @param col The {@code col(umn)} in the Sudoku grid that needs to be checked for the specified value
     * @param value The {@code value} that has to be searched for in the {@code col(umn)} for duplicity
     * @return A true/false for whether or not the {@code value} exists in the given {@code col(umn)}
     */
    private boolean valueInCol(int[][] board, int col, int value)
    {
        for (int i = 0; i < board.length; i++)
        {
            if (board[i][col] == value)
            {
                return true;
            }
        }

        return false;
    }

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

    private boolean valueInSub(int[][] board, int row, int col, int value)
    {
        int[] subSection = findSubSection(row, col);

        for (int i = (subSection[0] * 3); i < ((subSection[0] * 3) + 3); i++)
        {
            for (int j = (subSection[1] * 3); j < ((subSection[1] * 3) + 3); j++)
            {
                if (value == board[i][j])
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean anyEmptyCells(int[][] board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidity(int[][] board, int row, int col, int value)
    {
        return !valueInRow(board, row, value) && !valueInCol(board, col, value) && !valueInSub(board, row, col, value);
    }

    private void setCell(int[][] board, int row, int col)
    {
        ArrayList<Integer> usableNumbers = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            usableNumbers.add(i+1);
        }

        // while we still have numbers to try and have not found a valid number
        while (!usableNumbers.isEmpty()) {

            // pick random number from list of available numbers
            int valueIndex = SudokuTools.randRange(usableNumbers.size());
            int value = usableNumbers.get(valueIndex);

            int[] activeCell = {row, col};

            // check if generated number is valid
            if (checkValidity(board, activeCell[0], activeCell[1], value))
            {
                // add number to square
                board[activeCell[0]][activeCell[1]] = value;

                if (anyEmptyCells(this.board))
                {
                    if (col == 8)
                    {
                        setCell(this.board, row + 1, 0);
                    } else
                    {
                        setCell(this.board, row, col + 1);
                    }
                }
                break;
            }
            else
            {
                // remove number from list of available numbers
                usableNumbers.remove(valueIndex);
                // if we are out of numbers, stop trying to find a number
                if (usableNumbers.isEmpty()) {
                    if (col == 0)
                    {
                        setCell(this.board, row, 0);
                    }
                    else
                    {
                        setCell(this.board, row, col - 1);
                    }
                }
            }

        }
    }

    private void populateBoard()
    {
        setCell(this.board, 0, 0);
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
