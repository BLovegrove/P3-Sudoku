package SudokuRenderer;

public class MenuGraphic
{
    private String[] menuLines = new String[20];

    private void prepMenuTop()
    {
        this.menuLines[0] = "    ╔═════════════════════════════════╗";
    }

    private void prepMenuCore()
    {
        // FILE IO CLASS : RETURN ARRAY OF FILE NAMES AS String[]
        String[] fileNames = {"I am a file one", "filename.test", "filenameyayyyyy", "filesASDASDASD"};

        // 9 lines of text
    }

    private void prepMenuEnd()
    {
        this.menuLines[19] = "    ╚═════════════════════════════════╝";
    }
}
