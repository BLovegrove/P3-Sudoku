package SudokuRenderer.Startup;

/***
 * Simple dialogue box to mark out how big the game area is for rendering purposes
 */
public class ViewCalibrator
{
    /***
     * Draws the 87 x 20 character box showing the user where to resize their console window to.
     * Prints straight to console.
     */
    public static void  renderGuide(int viewHeight)
    {
        System.out.println("Please ensure your console window is at least the width and height of the box below :    ");
        System.out.println("+---------------------------------------------------------------------------------+");
        for(int i = 0; i < (viewHeight + 2); i++)
        {
            System.out.println(String.format("|%81s|", " "));
        }
        System.out.println("+---------------------------------------------------------------------------------+");
    }
}
