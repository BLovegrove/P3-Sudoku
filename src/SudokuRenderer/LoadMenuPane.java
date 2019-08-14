package SudokuRenderer;

/***
 * Makes a paginated ASCII-graphic menu to load previously saved sudoku boards from - all automatically generated
 */
public class LoadMenuPane
{
    /***
     * An array of all the file names excluding extensions in the saves folder
     */
    private String[] fileNames;
    /***
     * Holds the current page position to display every time the menu redraws
     */
    private int pageNumber;
    /***
     * The max number of pages that can be viewed based on the number of saves in the folder
     */
    private int maxPages;
    /***
     * Each individual line of the menu's pane stored as an array
     */
    private String[] menuLines = new String[20];

    //TODO : remove this when file IO complete
    private final String[] TEST_FILES = new String[]{
            "THis is a test 1",
            "THis is a test 2",
            "THis is a test 3",
            "THis is a test 4",
            "THis is a test 5",
            "THis is a test 6",
            "THis is a test 7",
            "THis is a test 8",
            "THis is a test 9",
            "THis is a test 10",
            "THis is a test 11",
            "THis is a test 12",
            "THis is a test 13",
            "THis is a test 14",
            "THis is a test 15",
            "THis is a test 16",
            "THis is a test 17",
            "THis is a test 18",
            "THis is a test 19",
            "THis is a test 20",
            "THis is a test 21",
            "THis is a test 22"
    };

    /***
     * standard constructor to initialise the menu page to 1 and get file list / prep default menu state
     */
    public LoadMenuPane()
    {
        this.pageNumber = 1;
        updateFiles();
        prepMenu();
    }

    /***
     * Construct and empty list as a starting point for the menu
     */
    private void prepMenu()
    {
        drawPageIndicator();
        this.menuLines[1] = "  ╔═══════════════════════════════════╗";
        for (int i = 0; i < 9; i++)
        {
            updateListItem(i, "");
            if (i != 8)
            {
                this.menuLines[((i + 1)* 2) + 1] = "  ╠═══════════════════════════════════╣";
            }
        }
        this.menuLines[19] = "  ╚═══════════════════════════════════╝";
    }

    /***
     * Draws / re-draws the 'page: x of y' text on top of the menu based on {@link #pageNumber} and {@link #maxPages}<br>
     * respectively
     */
    private void drawPageIndicator()
    {
        this.menuLines[0] = String.format(
                "   %-36s", " Page: " + this.pageNumber + " of " + this.maxPages
        );
    }

    /***
     * Used to call the (__FILE IO CLASS#METHOD LINK__) method, fetching a list of filenames to be displayed in the list
     */
    private void updateFiles()
    {
        // names can be at max 26 characters exc. file extension
        // TODO : FILE IO CLASS : RETURN ARRAY OF FILE NAMES AS String[]
        this.fileNames = TEST_FILES; // delete this line when IO class built
        this.maxPages = Math.round((this.fileNames.length / 9) + 1);
    }

    /***
     * Updates an individual line of the graphical list based on its relative position in said list (0-8)
     * @param itemIndex The position of the graphical list item you want to change
     * @param fileName The new file name (- extension) to replace the old one currently in-list
     */
    private void updateListItem(int itemIndex, String fileName)
    {
        int absIndex = (itemIndex + 1) * 2;
        if (fileName.equals(""))
        {
            this.menuLines[absIndex] = String.format("  ║%-35s║", " ");
        }
        else
        {
            this.menuLines[absIndex] = String.format("  ║%-35s║", " "+(itemIndex+1)+") "+fileName+".txt");
        }
    }

    /***
     * Get the current maximum number of pages available
     * @return Integer value as boundary for range 0 -> maxPages
     */
    public int getMaxPages()
    {
        return this.maxPages;
    }

    /***
     * Updates the current page value with the new one and redraws the list accordingly
     * @param pageNumber the page number in range 0 < {@link #maxPages}
     */
    public void setPage(int pageNumber)
    {
        // pageNumber has to be between 0 and maxPages(excl.)
        this.pageNumber = pageNumber;
        drawPageIndicator();
    }

    /***
     * Draws / re-draws all the current list items and constructs it into a String[] array
     * @return The String[] array representing the LoadMenu's pane for use in a {@link ViewRenderer}
     */
    public String[] draw()
    {
        updateFiles();
        for (int i = 0; i < 9; i++)
        {
            int absIndex = (9 * (this.pageNumber - 1)) + i;
            if (absIndex < this.fileNames.length)
            {
                updateListItem(i, this.fileNames[absIndex]);
            }
            else
            {
                updateListItem(i, "");
            }
        }
        return this.menuLines;
    }
}
