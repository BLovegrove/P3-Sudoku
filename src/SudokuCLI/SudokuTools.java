package SudokuCLI;

import FileIO.SaveData;
import SudokuGen.Generator;

import java.io.File;
import java.io.IOException;
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
