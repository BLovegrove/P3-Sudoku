package SudokuCLI;

import SudokuCLI.Managers.LoadManager;
import SudokuCLI.Managers.MenuManager;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.ViewRenderer;

public class Main
{

    public static void main(String[] args)
    {
        String[] TEST_FILES = new String[]{
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

        // CALIBRATE WINDOW : HEIGHT 20 LINES
        ViewCalibrator.calibrate(20);

        ViewRenderer view = new ViewRenderer();
        MenuManager mainMenu = new MenuManager();

        boolean running = true;

        while (running)
        {
            int menuSelected = mainMenu.pickMenuItem(view);

            switch (menuSelected)
            {
                case 0:
                {
                    running = false;
                    break;
                }
                case 1:
                {
                    // START NEW EASY GAME
                    break;
                }
                case 2:
                {
                    // START NEW NORMAL GAME
                    break;
                }
                case 3:
                {
                    // START NEW HARD GAME WITH LUDICROUS = FALSE
                    break;
                }
                case 4:
                {
                    // START NEW HARD GAME WITH LUDICROUS = TRUE
                    break;
                }
                case 5:
                {
                    LoadManager loadManager = new LoadManager();
                    String gameID = loadManager.loadGame(view, TEST_FILES);

                    if (!gameID.isEmpty())
                    {
                        // LOAD GAME BASED ON SAVE DATA IN ./saves/gameID.txt
                    }

                    break;
                }
            }
        }
    }
}
