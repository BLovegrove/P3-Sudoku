package SudokuRenderer.InfoPanels;

/***
 * The information panel to provide the user with commands to use when playing the sudoku game, as well as response<br>
 * from the system via the status message
 */
public class GameInfo extends InfoPanel
{
    /***
     * Standard constructor to instantiate Board's version of the InfoPanel layout
     */
    public GameInfo(String gameName)
    {
        super(
            String.format("  Save : %-35s,", gameName) +
            "  ╔════════════════════════════════════════╗," +
            "  ║       Commands Menu & Status Bar       ║," +
            "  ╠════════════════════════════════════════╣," +
            "  ║ Fill (A-I)(1-9) : e.g. Fill A1         ║," +
            "  ║  > Fills a cell. 'C' to cancel fill    ║," +
            "  ║ Clear (A-I)(1-9) : e.g. Clear D8       ║," +
            "  ║  > Clears the value of a cell          ║," +
            "  ║ Undo                                   ║," +
            "  ║  > Undoes your last board move         ║," +
            "  ║ Restart                                ║," +
            "  ║  > Restarts the game with a new board  ║," +
            "  ║ Save                                   ║," +
            "  ║  > Saves your board to play later      ║," +
            "  ║                                        ║," +
            "  ║ Exit                                   ║," +
            "  ║  > Exits the game to the main menu     ║," +
            "  ╠════════════════════════════════════════╣,"
        );
    }
}
