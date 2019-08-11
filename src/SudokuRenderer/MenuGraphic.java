package SudokuRenderer;

import com.sun.xml.internal.ws.util.StringUtils;

public class MenuGraphic
{
    private String[] menuLines;

    private void renderMenu(String statusMessage)
    {

        int statusPadStart;
        int statusPadEnd;

        if (statusMessage.length() % 2 == 0)
        {
            statusPadStart = statusPadEnd = ((36 - statusMessage.length()) / 2) - 1;
        }
        else
        {
            statusPadStart = ((35 - statusMessage.length()) / 2) - 1;
            statusPadEnd = ((37 - statusMessage.length()) / 2) - 1;
        }

        statusMessage = String.format("%" + statusPadStart + "s " + statusMessage + " %" + statusPadEnd + "s", "", "");

        // board size: 42x20 characters
        this.menuLines = String.format(

                "  ╔════════════════════════════════════════╗," +
                "  ║       Commands Menu & Status Bar       ║," +
                "  ╠════════════════════════════════════════╣," +
                "  ║ Fill (A-I)(1-9) : e.g. Fill A1         ║," +
                "  ║  ↳ Fills a cell                        ║," +
                "  ║ Clear (A-I)(1-9) : e.g. Clear D8       ║," +
                "  ║  ↳ Clears the value of a cell          ║," +
                "  ║ Restart                                ║," +
                "  ║  ↳ Restarts the game with a new board  ║," +
                "  ║ Games (page) : e.g. Games or Games 1   ║," +
                "  ║  ↳ Lists all saved games in pages      ║," +
                "  ║ Save ('name') : e.g. Save 'test 1'     ║," +
                "  ║  ↳ Saves your board to play later      ║," +
                "  ║ Load ('name') : e.g. Load 'test 1'     ║," +
                "  ║  ↳ Loads a previous board to play      ║," +
                "  ║ Delete ('name') : e.g. Delete 'test 1' ║," +
                "  ║  ↳ Deletes a save game by name         ║," +
                "  ╠════════════════════════════════════════╣," +
                "  ║> %s <║," +
                "  ╚════════════════════════════════════════╝",
                statusMessage

        ).split(",");
    }

    public String[] draw(String statusMessage)
    {
        renderMenu(statusMessage);
        return this.menuLines;
    }
}
