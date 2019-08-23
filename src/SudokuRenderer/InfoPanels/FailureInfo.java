package SudokuRenderer.InfoPanels;

import FileIO.SaveData;

/**
 * Class that prints the failure message observed if a ludicrous mode board is finished without a valid solution
 */
public class FailureInfo extends InfoPanel
{

    /**
     * Prints the failure message to console when a ludicrous mode board is failed
     * @param save the save containing the ludicrous game in question
     */
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
