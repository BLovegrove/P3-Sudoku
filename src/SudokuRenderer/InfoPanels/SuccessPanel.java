package SudokuRenderer.InfoPanels;

import FileIO.SaveData;
import SudokuCLI.Gameplay.DifficultyLevel;

/***
 * Simple class that produces a success window when a sudoku game is complete. Uses data from their {@link SaveData}
 * to show the difficulty level of the game in the success panel.
 */

public class SuccessPanel extends InfoPanel
{
    /***
     * Method that produces a success window for a completed Sudoku game
     * @param save - the save file for the completed game
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
