package SudokuRenderer.CustomPanels;

/***
 * Draws the static main menu element for users to use to start a game. The menu is 73 characters long, with 5 <br>
 * characters spacing either side for a total width of 87 characters
 */
public class MainMenu
{
    /***
     * The string defining the layout for the main menu
     */
    private String[] menu = (
            "            ╔═════════════════════════════════════════════════════════╗            ," +
            "╔═══╤═══╤═══╣        Sudoku-CLI: The Command Line Puzzle Game!        ╠═══╤═══╤═══╗," +
            "║ 9 │   │   ╠═════════════════════════════════════════════════════════╣   │ 4 │   ║," +
            "║───┼───┼───║  ----------------  1) New Easy Game  -----------------  ║───┼───┼───║," +
            "║   │ 1 │ 5 ║                                                         ║ 9 │ 2 │   ║," +
            "║───┼───┼───║  ---------------  2) New Normal Game  ----------------  ║───┼───┼───║," +
            "║   │ 2 │   ║                                                         ║   │   │ 1 ║," +
            "╠═══╪═══╪═══╣  ----------------  3) New Hard Game  -----------------  ╠═══╪═══╪═══╣," +
            "║   │   │ 6 ║                                                         ║ 7 │   │   ║," +
            "║───┼───┼───║  -----------  4) New Ludicrous Mode Game  ------------  ║───┼───┼───║," +
            "║ 2 │ 3 │   ║                                                         ║   │ 5 │ 9 ║," +
            "║───┼───┼───║  --------------  5) Load a saved game  ---------------  ║───┼───┼───║," +
            "║ 4 │   │   ║                                                         ║ 6 │ 3 │   ║," +
            "╠═══╪═══╪═══╣                                                         ╠═══╪═══╪═══╣," +
            "║   │ 5 │ 9 ║                                                         ║ 4 │ 8 │ 3 ║," +
            "║───┼───┼───║                                                         ║───┼───┼───║," +
            "║   │   │ 2 ║  ------------------  0) Exit Game  -------------------  ║   │   │ 5 ║," +
            "║───┼───┼───║                                                         ║───┼───┼───║," +
            "║ 1 │   │ 3 ║       (Enter the number of the option you'd like)       ║ 2 │   │   ║," +
            "╚═══╧═══╧═══╩═════════════════════════════════════════════════════════╩═══╧═══╧═══╝"
    ).split(",");

    /***
     * Returns the sudoku game's main menu graphic
     * @return String[] representing the main menu's panel for use in a {@link SudokuRenderer.ViewRenderer}
     */
    public String[] draw()
    {
        return this.menu;
    }
}
