package SudokuRenderer.InfoPanes;

public class Board
{
    private String[] menuLines = new String[20];

    public Board()
    {
        prepMenu();
    }

    private void prepMenu()
    {
        String[] menuTop = (

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
                "  ╠════════════════════════════════════════╣,"

        ).split(",");

        System.arraycopy(menuTop, 0, this.menuLines, 0, 18);

        setStatus("");

        this.menuLines[19] = "  ╚════════════════════════════════════════╝";
    }

    public void setStatus(String statusMessage)
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

        statusMessage = String.format("  ║> %s <║", statusMessage);

        this.menuLines[18] = statusMessage;
    }

    public String[] draw()
    {
        return this.menuLines;
    }
}
