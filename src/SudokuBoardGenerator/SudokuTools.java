package SudokuBoardGenerator;

import java.util.Random;

public class SudokuTools
{
    public static int randRange(int bound)
    {
        Random randGen = new Random();
        return randGen.nextInt(bound);
    }

    public static int randRange(int min, int max)
    {
        Random randGen = new Random();
        return randGen.nextInt((max - min) + 1) + min;
    }

    public static int randInt()
    {
        Random randGen = new Random();
        return randGen.nextInt(9) + 1;
    }

    public static boolean isValueInRow(int[] row, int scanValue)
    {
        boolean isValueInArray = false;
        for (int currentValue : row)
        {
            if (currentValue == scanValue)
            {
                isValueInArray = true;
                break;
            }
        }
        return isValueInArray;
    }
}
