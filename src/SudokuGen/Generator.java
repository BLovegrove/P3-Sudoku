package SudokuGen;

import SudokuCLI.Gameplay.DifficultyLevel;
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
     * The boolean to keep track of whether or not a valid board has been produced by {@link #generate()}
     */
    private boolean boardFinished = false;

    /***
     * The master list that stores all current potential cell values for all cells
     */
    private ArrayList<List<Integer>> cellValues = new ArrayList<>();

    /***
     * Iterates 81 times to produce a full list of size 81 {@code List<Integer>} elements with randomised order using
     * {@link SudokuTools#shuffledNine()}.
     */
    private static void populateCellValues(ArrayList<List<Integer>> cellValues)
    {
        for (int i = 0; i < 81; i++)
        {
            cellValues.add(SudokuTools.shuffledNine());
        }
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
        if (board[row][col] == 0 && !SudokuTools.boardFull(board) && !this.boardFinished)
        {

            List<Integer> cellNumbers = getCellValues(row, col);
            boolean cellSet = false;

            while (!cellNumbers.isEmpty())
            {

                int cellValue = cellNumbers.get(cellNumbers.size() - 1);
                boolean legal = SudokuTools.cellValid(row, col, cellValue, board);

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
        populateCellValues(this.cellValues);
        solveCell(board, 0,0, true);
        return board;
    }

    /**
     * This variable counts the number of solutions present in a puzzle board
     */
    private int solutionCount;

    /**
     * This function takes a complete Sudoku board as input and outputs a "puzzle" board - an incomplete board that has
     * only one solution. The complexity of the board varies based on the difficulty level enumerator set earlier in
     * the program. Uses the {@link #oneSolution(int[][])} class to ensure there is one solution to the puzzle board.
     * @param reference The complete board that the function takes as input
     * @param difficulty The enum difficulty level enumerator
     * @return the complete puzzle board with one possible solution.
     */
    public int[][] unSolver(int[][] reference, DifficultyLevel difficulty)
    {
        int[][] board = SudokuTools.cloneArray(reference);
        int cellsToClear;
        ArrayList<int[]> triedCells = new ArrayList<>();
        int row;
        int col;
        switch (difficulty)
        {
            case EASY:
            {
                cellsToClear = 5;
                break;
            }
            case MEDIUM:
            {
                cellsToClear = 30;
                break;
            }
            case HARD:
            {
                cellsToClear = 55;
                break;
            }
            case LUDICROUS:
            {
                cellsToClear = 63;
                break;
            }
            default:
            {
                cellsToClear = 40;
                break;
            }
        }
        while (cellsToClear > 0)
        {
            solutionCount = 0;
            do
            {
                row = SudokuTools.randRange(9);
                col = SudokuTools.randRange(9);
            }
            while (board[row][col] == 0 && triedCells.contains(new int[]{row,col}));
            triedCells.add(new int[]{row, col});
            int valueBackup = board[row][col];
            board[row][col] = 0;
            int[][] tempBoard = SudokuTools.cloneArray(board);
            if (oneSolution(tempBoard))
            {
                cellsToClear --;
            }
            else
            {
                board[row][col] = valueBackup;
            }
        }
        return board;
    }

    /**
     * This function checks to see whether an input puzzle board has multiple solutions. It returns true if there
     * is one solution to the board and outputs false if there are more.
     * @param board The input puzzle board
     * @return true if the input board has one possible solution, false if there are more
     */
    private boolean oneSolution(int[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] == 0)
                {
                    int numSolutions = 0;
                    int solution = 0;

                    for (int value = 1; value < 10; value++)
                    {
                        if (SudokuTools.cellValid(i, j, value, board))
                        {
                            solution = value;
                            numSolutions ++;
                        }
                    }
                    if (numSolutions != 1)
                    {
                        return false;
                    }
                    else
                    {
                        board[i][j] = solution;

                        if (!SudokuTools.boardFull(board))
                        {
                            return oneSolution(board);
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return (solutionCount == 1);
    }
}