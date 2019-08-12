package SudokuRenderer;

public class LoadInfoPane
{
    private String[] menuLines = new String[20];

    public LoadInfoPane()
    {
        prepMenu();
    }

    private void prepMenu()
    {
        String[] menuTop = (

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
