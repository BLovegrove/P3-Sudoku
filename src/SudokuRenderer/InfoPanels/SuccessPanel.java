package SudokuRenderer.InfoPanels;

import FileIO.SaveData;
import SudokuCLI.Gameplay.DifficultyLevel;

public class SuccessPanel extends InfoPanel
{
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
                        "  ║  > "+ String.format("%-35s", save.getMoves() + " moves! Nice work.") +" ║," +
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
