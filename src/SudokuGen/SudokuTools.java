package SudokuGen;

import java.util.*;

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

    public static List<Integer> shuffledNine()
    {
        List<Integer> numbers = new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        Collections.shuffle(numbers, new Random(SudokuTools.randInt()));

        return numbers;
    }
}
