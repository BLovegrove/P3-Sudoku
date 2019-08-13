package SudokuRenderer.Startup;

public class ViewCalibrator
{
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
