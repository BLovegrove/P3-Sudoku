package SudokuCLI;

import SudokuCLI.Managers.LoadManager;
import SudokuCLI.Managers.MenuManager;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.ViewRenderer;

import java.util.ArrayList;

public class Main
{

    public static void main(String[] args)
    {
        ArrayList<String> TEST_FILES = new ArrayList<>();

        for (int i = 0; i < 25; i++)
        {
            TEST_FILES.add(String.format("Test File %1s", i));
        }

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
