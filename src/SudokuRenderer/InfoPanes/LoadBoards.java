package SudokuRenderer.InfoPanes;

/***
 * The information panel to provide the user with commands to use when loading a sudoku board, as well as response<br>
 * from the system via the status message
 */
public class LoadBoards extends InfoPane
{
    /***
     * Standard constructor to instantiate LoadBoards' version of the InfoPanel layout
     */
    public LoadBoards()
    {
        super(
            "  ╔════════════════════════════════════════╗," +
            "  ║       Commands Menu & Status Bar       ║," +
            "  ╠════════════════════════════════════════╣," +
            "  ║ Page (1-9) : e.g. Page 9               ║," +
            "  ║  ↳ Changes the current saves page      ║," +
            "  ║ Load (1-9) : e.g. Load 6               ║," +
            "  ║  ↳ Loads a selected save game          ║," +
            "  ║ Rename (A-R)(1-9) 'new name' :         ║," +
            "  ║  ↳ e.g. Rename A2 'test 5'             ║," +
            "  ║  ↳ Renames an existing save game       ║," +
            "  ║ Delete (A-R)(1-9) : e.g. Delete H3     ║," +
            "  ║  ↳ Deletes a selected save game        ║," +
            "  ║ Back                                   ║," +
            "  ║  ↳ Returns you to the game screen      ║," +
            "  ║                                        ║," +
            "  ║                                        ║," +
            "  ║                                        ║," +
            "  ╠════════════════════════════════════════╣,"
        );
    }
}
