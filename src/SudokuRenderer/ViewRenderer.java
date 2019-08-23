package SudokuRenderer;

public class ViewRenderer
{
    /***
     * The 20-line-deep String[] array that makes up the full game graphic (combined {@link #primaryPanel} and<br>
     * {@link #secondaryPanel})
     */
    private String[] view = new String[20];
    /***
     * The left hand side view window panel that makes up the total game {@link #view}
     */
    private String[] primaryPanel = new String[20];
    /***
     * The right hand side view window panel that makes up the total game {@link #view}
     */
    private String[] secondaryPanel = new String[20];

    /***
     * Simple method to iterate through each line (item) of the {@link #view} and equate it to the combined <br>
     * {@link #primaryPanel} and {@link #secondaryPanel}
     */
    private void update()
    {
        for (int i = 0; i < 20; i++)
        {
            this.view[i] = this.primaryPanel[i] + this.secondaryPanel[i];
        }
    }

    /***
     * Set the left-hand-side panel for the next draw
     * @param panel The String[] array to override the current {@link #primaryPanel}
     */
    public void setPrimaryPanel(String[] panel)
    {
        this.primaryPanel = panel;
        update();
    }

    /***
     * Set the right-hand-side panel for the next draw
     * @param panel The String[] array to override the current {@link #secondaryPanel}
     */
    public void setSecondaryPanel(String[] panel)
    {
        this.secondaryPanel = panel;
        update();
    }

    /***
     * Sets both left and right window panels for the next draw
     * @param primaryPanel The String[] array to override the current {@link #primaryPanel}
     * @param secondaryPanel The String[] array to override the current {@link #secondaryPanel}
     */
    public void setPanels(String[] primaryPanel, String[] secondaryPanel)
    {
        this.primaryPanel = primaryPanel;
        this.secondaryPanel = secondaryPanel;
        update();
    }

    /***
     * Allows direct alteration of the entire view array on one go, instead of a single view panel
     * @param fullScreenPanel The String[] array to override the current {@link #view}
     */
    public void setView(String[] fullScreenPanel)
    {
        this.view = fullScreenPanel;
    }

    /***
     * Draws the complete view window to the console, line-by-line
     */
    public void render()
    {
        clearScreen();
        for (String line : this.view)
        {
            System.out.println(line);
        }
    }

    /**
     * Attempts to clear the console - were unable to get the escape sequence to work
     */
    private static void clearScreen() {
        for (int i = 0; i < 40; i++)
        {
            System.out.println("");
        }
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
    }
}
