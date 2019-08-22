package SudokuRenderer.InfoPanels;

public class InfoPanel
{
    /***
     * Each individual line of the panel to be used in a {@link SudokuRenderer.ViewRenderer}
     */
    private String[] menuLines = new String[20];
    private String menuString;

    /***
     * Standard constructor to prepare the default (empty) menu before propagating it.
     */
    public InfoPanel(String menuString)
    {
        this.menuString = menuString;
        prepMenu();
    }

    /***
     * Pieces together a default (empty) commands menu and status bar to be<br>
     * used as a {@link SudokuRenderer.ViewRenderer} panel
     */
    private void prepMenu()
    {
        String[] menuTop = (this.menuString).split(",");

        System.arraycopy(menuTop, 0, this.menuLines, 0, 18);

        setStatus("");

        this.menuLines[19] = "  ╚════════════════════════════════════════╝";
    }

    /***
     * Alters only the status line of the menu's panel ({@link #menuLines}) for faster graphic update
     * @param statusMessage The string to be set as the status message when redrawn. Max 36 characters.
     */
    public void setStatus(String statusMessage)
    {
        int statusPadStart;
        int statusPadEnd;

        // shouldn't be triggered if input is monitored, but just in case.
        if (statusMessage.length() > 36)
        {
            statusMessage = "ERROR: STATUS MESSAGE TOO LONG";
        }

        // sets up padding for the status message so it stays centered
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

    /***
     * Simply returns the menu panel
     * @return String[] representing this menu's panel for a {@link SudokuRenderer.ViewRenderer}
     */
    public String[] draw()
    {
        return this.menuLines;
    }
}
