package SudokuRenderer;

public class ViewRenderer
{
    /***
     * The 20-line-deep String[] array that makes up the full game graphic (combined {@link #primaryPane} and<br>
     * {@link #secondaryPane})
     */
    private String[] view = new String[20];
    /***
     * The left hand side view window pane that makes up the total game {@link #view}
     */
    private String[] primaryPane = new String[20];
    /***
     * The right hand side view window pane that makes up the total game {@link #view}
     */
    private String[] secondaryPane = new String[20];

    /***
     * Simple method to iterate through each line (item) of the {@link #view} and equate it to the combined <br>
     * {@link #primaryPane} and {@link #secondaryPane}
     */
    private void update()
    {
        for (int i = 0; i < 20; i++)
        {
            this.view[i] = this.primaryPane[i] + this.secondaryPane[i];
        }
    }

    /***
     * Set the left-hand-side pane for the next draw
     * @param pane The String[] array to override the current {@link #primaryPane}
     */
    public void setPrimaryPane(String[] pane)
    {
        this.primaryPane = pane;
        update();
    }

    /***
     * Set the right-hand-side pane for the next draw
     * @param pane The String[] array to override the current {@link #secondaryPane}
     */
    public void setSecondaryPane(String[] pane)
    {
        this.secondaryPane = pane;
        update();
    }

    /***
     * Sets both left and right window panes for the next draw
     * @param primaryPane The String[] array to override the current {@link #primaryPane}
     * @param secondaryPane The String[] array to override the current {@link #secondaryPane}
     */
    public void setPanes(String[] primaryPane, String[] secondaryPane)
    {
        this.primaryPane = primaryPane;
        this.secondaryPane = secondaryPane;
        update();
    }

    /***
     * Allows direct alteration of the entire view array on one go, instead of a single view pane
     * @param fullScreenPane The String[] array to ovveride the current {@link #view}
     */
    public void setView(String[] fullScreenPane)
    {
        this.view = fullScreenPane;
    }

    /***
     * Draws the complete view window to the console, line-by-line
     */
    public void render()
    {
        for (String line : this.view)
        {
            System.out.println(line);
        }
    }
}
