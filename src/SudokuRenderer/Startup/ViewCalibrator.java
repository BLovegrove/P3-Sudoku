package SudokuRenderer.Startup;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

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
        System.out.println("Please ensure your console window is at least the width and height of the box below :");
        System.out.println("+-----------------------------------------------------------------------------------+");
        for(int i = 0; i < (viewHeight - 1); i++)
        {
            System.out.println(String.format("|%81s|", " "));
        }
        System.out.println("+-----------------------------------------------------------------------------------+");
        System.out.println("Please ensure your console window is at least the width and height of the box above :");
        System.out.println("");
    }


    public static void calibrate(int windowHeight)
    {
        System.out.println("A quick guide before you begin: ");
        System.out.println("");
        System.out.println(" - Easy games are meant, in this case, for testing the game. It only leaves 5 squares");
        System.out.println("   unfilled and like all bar one difficulties, will prevent invalid cell placement.");
        System.out.println("");
        System.out.println(" - Medium games are the average game you'll find in a newspaper sudoku puzzle.");
        System.out.println("");
        System.out.println(" - Hard games leave few empty squares and are for more seasoned puzzlers");
        System.out.println("");
        System.out.println(" - Ludicrous games are as hard as a Sudoku puzzle can physically be, and gives no");
        System.out.println("   warning if you place an invalid number. You will only know when you've filled all");
        System.out.println("   the cells on the board whether or not you succeeded. Ludicrous games are deleted");
        System.out.println("   if you lose the game, and like all games, saved if you win.");
        renderGuide(windowHeight);
        System.out.println("Enter any key to continue : ");
        String response = new Scanner(System.in).nextLine();
    }
}
