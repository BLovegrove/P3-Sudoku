package SudokuRenderer.InfoPanels;

import FileIO.SaveData;

public class FailedInfo extends InfoPanel
{
    public FailedInfo(SaveData save)
    {
        super(
                String.format("  Save : %-35s,", save.boardName) +
                "  ╔════════════════════════════════════════╗," +
                "  ║ FAILURE! Your Sudoku board isn't valid ║," +
                "  ╠════════════════════════════════════════╣," +
                "  ║                                        ║," +
                "  ║ "+
                String.format(
                        "%-38s",
                        "Your game was a ludicrous level board."
                ) + " ║," +
                "  ║                                        ║," +
                "  ║ You filled the board in :              ║," +
                "  ║  > "+
                String.format(
                        "%-36s",
                        save.getMoves() +
                        " moves! Good try."
                ) +" ║," +
                "  ║                                        ║," +
                "  ║ Sorry - if you're seeing this then     ║," +
                "  ║ your board was ludicrously difficult   ║," +
                "  ║ and you failed.                        ║," +
                "  ║                                        ║," +
                "  ║ Thus, it has been deleted. Good luck   ║," +
                "  ║ on your next run!                      ║," +
                "  ║                                        ║," +
                "  ╠════════════════════════════════════════╣,"
        );
    }
}
