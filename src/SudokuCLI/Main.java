package SudokuCLI;

import FileIO.IOTools;
import SudokuCLI.Gameplay.DifficultyLevel;
import SudokuCLI.Gameplay.GameManager;
import SudokuCLI.MenuHandlers.LoadManager;
import SudokuCLI.MenuHandlers.MenuManager;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.ViewRenderer;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        // CALIBRATE WINDOW : HEIGHT 20 LINES (-2 for status message)
        ViewCalibrator.calibrate(18);

        // CREATE MAIN MENU
        MenuManager mainMenu = new MenuManager();

        // CREATE GAME INSTANCE
        GameManager game = new GameManager();

        // CREATE RENDERER
        ViewRenderer view = new ViewRenderer();

        // SET DEFAULT MENU ERROR TO 'NONE'
        String menuError = "";

        boolean running = true;
        while (running)
        {
            // ASK FOR RESPONSE
            String menuResponse = mainMenu.pickMenuItem(view, menuError);

            // INIT EMPTY RESPONSE VAR
            int menuSelected = 99;

            // EXECUTE RESPONSE
            try
            {
                menuSelected = Integer.parseInt(menuResponse);
                menuError = "";
            }
            catch (NumberFormatException e)
            {
                // SET NEW MENU ERROR FOR NEXT LOOP
                menuError = menuResponse;
            }

            if (menuError.isEmpty())
            {
                switch (menuSelected)
                {
                    case 0:
                    {
                        running = false;
                        break;
                    }
                    case 1:
                    {
                        game.newGame(view, DifficultyLevel.EASY);

                        break;
                    }
                    case 2:
                    {
                        game.newGame(view, DifficultyLevel.MEDIUM);

                        break;
                    }
                    case 3:
                    {
                        game.newGame(view, DifficultyLevel.HARD);

                        break;
                    }
                    case 4:
                    {
                        game.newGame(view, DifficultyLevel.LUDICROUS);

                        break;
                    }
                    case 5:
                    {
                        LoadManager loadManager = new LoadManager();
                        ArrayList<String> fileNames = IOTools.getSaveNames();
                        String gameID = loadManager.loadGame(view, fileNames);

                        if (!gameID.isEmpty())
                        {
                            game.loadGame(view, gameID);
                        }

                        break;
                    }
                    default:
                    {

                    }
                }
            }
        }
    }
}
