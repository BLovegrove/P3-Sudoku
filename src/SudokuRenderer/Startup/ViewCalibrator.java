package SudokuRenderer.Startup;

import java.util.Scanner;

/***
 * Simple dialogue box to mark out the size of the game area for rendering purposes
 */
public class ViewCalibrator
{
    /***
     * Draws the 87 x 20 character box showing the user how to size their console.
     * Prints straight to console.
     */
    private static void renderGuide(int viewHeight)
    {
        System.out.println("Please ensure your console window is at least the width and height of the box below :    ");
        System.out.println("+---------------------------------------------------------------------------------+");
        for(int i = 0; i < (viewHeight); i++)
        {
            System.out.println(String.format("|%81s|", " "));
        }
        System.out.println("+---------------------------------------------------------------------------------+");
    }


    public static void calibrate(int windowHeight)
    {
        renderGuide(windowHeight);
        System.out.println("Enter any key to continue : ");
        String response = new Scanner(System.in).nextLine();
    }
}
