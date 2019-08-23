package SudokuRenderer.InfoPanels;

import FileIO.SaveData;

public class FailureInfo extends InfoPanel
{
    public FailureInfo(SaveData save)
    {
        super(
                String.format("  Save : %-35s,", save.boardName) +
                "  ╔════════════════════════════════════════╗," +
                "  ║ FAILURE! Your Sudoku board isn't valid ║," +
                "  ╠════════════════════════════════════════╣," +
                "  ║                                        ║," +
                "  ║ Your game was a ludicrous level board. ║," +
                "  ║                                        ║," +
                "  ║ You filled the board in :              ║," +
                "  ║  > "+
                String.format(
                        "%-35s",
                        save.getMoves() +
                        " moves! Good try."
                ) +" ║," +
                "  ║                                        ║," +
                "  ║ Sorry - if you're seeing this then     ║," +
                "  ║ your board was ludicrously difficult   ║," +
                "  ║ and you failed.                        ║," +
                "  ║                                        ║," +
                "  ║ Thus it has been deleted. Good luck    ║," +
                "  ║ on your next run!                      ║," +
                "  ║                                        ║," +
                "  ╠════════════════════════════════════════╣,"
        );
    }
}
