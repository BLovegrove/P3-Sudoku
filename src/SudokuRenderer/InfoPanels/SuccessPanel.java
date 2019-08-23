package SudokuRenderer.InfoPanels;

import FileIO.SaveData;
import SudokuCLI.Gameplay.DifficultyLevel;

/***
 * The image shown when the user completes a sudoku board - uses data form their completed {@link SaveData} object
 * to fill in the gaps and let them know how they did
 */
public class SuccessPanel extends InfoPanel
{
    /***
     * Standard constructor to load the panel data and get
     * @param save
     */
    public SuccessPanel(SaveData save)
    {
        super(
                String.format("  Save : %-35s,", save.boardName) +
                "  ╔════════════════════════════════════════╗," +
                "  ║ CONGRATULATIONS! You filled the board! ║," +
                "  ╠════════════════════════════════════════╣," +
                "  ║                                        ║," +
                "  ║ "+
                String.format(
                        "%-38s", "Your game was "+
                        (save.getDifficulty() == DifficultyLevel.EASY ? "an " : "a ") +
                        save.getDifficulty().toString().toLowerCase() +
                        " level board."
                ) + " ║," +
                "  ║                                        ║," +
                "  ║ You completed the board in :           ║," +
                "  ║  > "+
                String.format(
                        "%-35s", save.getMoves() +
                                " moves! Nice work."
                ) +" ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ║                                        ║," +
                "  ╠════════════════════════════════════════╣,"
        );
    }
}
