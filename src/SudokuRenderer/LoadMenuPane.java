package SudokuRenderer;

public class LoadMenuPane
{
    private String[] fileNames;
    private int pageNumber;
    private int maxPages;
    private String[] menuLines = new String[20];

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

    public LoadMenuPane()
    {
        this.pageNumber = 1;
        updateFiles();
        prepMenu();
    }

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

    private void drawPageIndicator()
    {
        this.menuLines[0] = String.format(
                "   %-36s", " Page: " + this.pageNumber + " of " + this.maxPages
        );
    }

    private void updateFiles()
    {
        // names can be at max 26 characters exc. file extension
        // FILE IO CLASS : RETURN ARRAY OF FILE NAMES AS String[]
        this.fileNames = TEST_FILES; // delete this line when IO class built
        this.maxPages = Math.round((this.fileNames.length / 9) + 1);
    }

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

    public int getMaxPages()
    {
        return this.maxPages;
    }

    public void setPage(int pageNumber)
    {
        // pageNumber has to be > 0 but <= maxPages
        this.pageNumber = pageNumber;
        drawPageIndicator();
    }

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
