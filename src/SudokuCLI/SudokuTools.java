package SudokuCLI;

import FileIO.SaveData;
import SudokuGen.Generator;

import java.util.*;

/***
 * Contains some generic tools for generating a sudoku board added to a separate class for ease of use / modularity
 */
public class SudokuTools
{
    /***
     * Generates a randomised integer between 0 and the boundary exclusive
     * @param bound The boundary integer - 1 more than the max value selectable
     * @return The chosen integer between 0 and bound(exc.)
     */
    public static int randRange(int bound)
    {
        Random randGen = new Random();
        return randGen.nextInt(bound);
    }

    /***
     * Generates a randomised integer between two values (inclusive)
     * @param min The minimum value to be selected
     * @param max The maximum value to be selected
     * @return The chosen integer between min and max
     */
    public static int randRange(int min, int max)
    {
        Random randGen = new Random();
        return randGen.nextInt((max - min) + 1) + min;
    }

    /***
     * Generates a random integer from 1 to 9 inclusive for things like cell value seed generation in {@link Generator}
     * @return Integer from 1 to 9 inclusive
     */
    public static int randInt()
    {
        Random randGen = new Random();
        return randGen.nextInt(9) + 1;
    }

    /***
     * Used for generating a cells number List in {@link Generator}
     * @return List of scrambled integer values form 1->9 inclusive
     */
    public static List<Integer> shuffledNine()
    {
        List<Integer> numbers = new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        Collections.shuffle(numbers, new Random(SudokuTools.randInt()));

        return numbers;
    }



    /***
     * Part of the Sudoku solving algorithm to generate a complete board before reducing it to a game-ready board.<br>
     * This part handles scanning a row in the board for a given row to determine if there is any duplicity.
     * @param row The row in the Sudoku grid that needs to be checked for the specified value
     * @param value The value that has to be searched for in the row for duplicity
     * @return A true/false for whether or not the value exists in the given row
     */
    private static boolean rowValid(int row, int value, int[][] board)
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
    private static boolean columnValid(int col, int value, int[][] board)
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
    private static int[] findSubSection(int row, int col)
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
    private static boolean subsectionValid(int row, int col, int value, int[][] board)
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
    public static boolean cellValid(int row, int col, int value, int[][] board)
    {
        return rowValid(row, value, board) && columnValid(col, value, board) && subsectionValid(row, col, value, board);
    }

    public static boolean gameComplete(SaveData save)
    {
        if (save.getBoard() == save.getReference())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
