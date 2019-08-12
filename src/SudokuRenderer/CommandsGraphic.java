package SudokuRenderer;

import com.sun.xml.internal.ws.util.StringUtils;

public class CommandsGraphic
{
    private String[] menuLines;
    private String menuType;

    public CommandsGraphic(String menuType)
    {
        this.menuType = menuType;
    }

    private void renderMenu(String statusMessage)
    {

        int statusPadStart;
        int statusPadEnd;

        if (statusMessage.length() > 36)
        {
            statusMessage = "ERROR: STATUS MESSAGE TOO LONG";
        }

        if (statusMessage.length() % 2 == 0)
        {
            statusPadStart = statusPadEnd = ((36 - statusMessage.length()) / 2) - 1;
        }
        else
        {
            statusPadStart = ((35 - statusMessage.length()) / 2) - 1;
            statusPadEnd = ((37 - statusMessage.length()) / 2) - 1;
        }

        if (statusPadStart > 0 && statusPadEnd > 0)
        {
            statusMessage = String.format("%" + statusPadStart + "s " + statusMessage + " %" + statusPadEnd + "s", "", "");
        }
        else
        {
            if (statusMessage.length() != 36)
            {
                statusMessage = String.format(statusMessage + "%" + (36 - statusMessage.length()) + "s", "");
            }
        }

        switch (this.menuType)
        {
            case "board":
                // board size: 42x20 characters
                this.menuLines = String.format(

                             "  ╔════════════════════════════════════════╗," +
                                "  ║       Commands Menu & Status Bar       ║," +
                                "  ╠════════════════════════════════════════╣," +
                                "  ║ Fill (A-I)(1-9) : e.g. Fill A1         ║," +
                                "  ║  ↳ Fills a cell                        ║," +
                                "  ║ Clear (A-I)(1-9) : e.g. Clear D8       ║," +
                                "  ║  ↳ Clears the value of a cell          ║," +
                                "  ║ Undo / Redo                            ║," +
                                "  ║  ↳ Undoes / Re-does your last move     ║," +
                                "  ║ Restart                                ║," +
                                "  ║  ↳ Restarts the game with a new board  ║," +
                                "  ║ Save                                   ║," +
                                "  ║  ↳ Saves your board to play later      ║," +
                                "  ║ Load                                   ║," +
                                "  ║  ↳ Opens the 'load board' menu         ║," +
                                "  ║ Exit                                   ║," +
                                "  ║  ↳ Exits the game to the main menu     ║," +
                                "  ╠════════════════════════════════════════╣," +
                                "  ║> %s <║," +
                                "  ╚════════════════════════════════════════╝",
                        statusMessage

                ).split(",");
                break;

            case "loadMenu":
                // board size: 42x20 characters
                this.menuLines = String.format(

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
                               "  ╠════════════════════════════════════════╣," +
                               "  ║> %s <║," +
                               "  ╚════════════════════════════════════════╝",
                        statusMessage

                ).split(",");
        }

    }

    public String[] draw(String statusMessage)
    {
        renderMenu(statusMessage);
        return this.menuLines;
    }
}
