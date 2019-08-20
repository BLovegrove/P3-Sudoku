package SudokuRenderer.Startup;

/***
 * Draws the static main menu element for users to use to start a game. The menu is 73 characters long, with 5 <br>
 * characters spacing either side for a total width of 87 characters
 */
public class NewGame
{
    /***
     * The string defining the layout for the main menu
     */
    private String[] menu = (
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "╔═════════════════════════════════════════════════════════════════════════════════╗," +
            "║     Please enter a name for your new game. Must be less than 32 characters.     ║," +
            "╚═════════════════════════════════════════════════════════════════════════════════╝"
    ).split(",");

    /***
     * Returns the 'new game' graphic
     * @return String[] representing the new game screen's pane for use in a {@link SudokuRenderer.ViewRenderer}
     */
    public String[] draw()
    {
        return this.menu;
    }
}
