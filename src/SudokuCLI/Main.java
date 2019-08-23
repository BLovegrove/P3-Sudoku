package SudokuCLI;

import FileIO.IOTools;
import FileIO.SaveData;
import SudokuCLI.Gameplay.DifficultyLevel;
import SudokuCLI.Gameplay.GameManager;
import SudokuCLI.MenuHandlers.LoadManager;
import SudokuCLI.MenuHandlers.MenuManager;
import SudokuRenderer.InfoPanels.SuccessInfo;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.UtilityPanels.GameBoard;
import SudokuRenderer.ViewRenderer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // CALIBRATE WINDOW : HEIGHT 20 LINES (-2 for status message)
        ViewCalibrator.calibrate(18);

        // CREATE SAVE DIRECTORY IF IT DOESN'T ALREADY EXIST
        if (!Files.exists(Paths.get(IOTools.SAVE_DIR.getName())))
        {
            //noinspection ResultOfMethodCallIgnored
            IOTools.SAVE_DIR.mkdirs();
        }

        // CREATE MAIN MENU
        MenuManager mainMenu = new MenuManager();

        // CREATE GAME INSTANCE
        GameManager game = new GameManager();

        // CREATE RENDERER
        ViewRenderer view = new ViewRenderer();

        // SET DEFAULT MENU ERROR TO 'NONE'
        String menuError = "";

        // SET GAME COMPLETE FLAG TO FALSE;
        SaveData save = null;
        boolean gameComplete = false;

        boolean running = true;
        while (running)
        {
            // CHECK IF GAME IS COMPLETE
            if (gameComplete)
            {
                SuccessInfo successInfo = new SuccessInfo(save);
                successInfo.setStatus("Press any key to go back to the menu");

                GameBoard gameBoard = new GameBoard(save.getBoard());

                view.setPanels(gameBoard.draw(), successInfo.draw());
                view.render();
                String successResponse = new Scanner(System.in).nextLine();
                gameComplete = false;
            }

            // ASK FOR RESPONSE
            String menuResponse = mainMenu.pickMenuItem(view, menuError);

            // INIT EMPTY RESPONSE VAR
            int menuSelected = -1;

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
                        save = game.newGame(view, DifficultyLevel.EASY);
                        gameComplete = save.boardComplete();

                        break;
                    }
                    case 2:
                    {
                        save = game.newGame(view, DifficultyLevel.MEDIUM);
                        gameComplete = save.boardComplete();

                        break;
                    }
                    case 3:
                    {
                        save = game.newGame(view, DifficultyLevel.HARD);
                        gameComplete = save.boardComplete();

                        break;
                    }
                    case 4:
                    {
                        save = game.newGame(view, DifficultyLevel.LUDICROUS);
                        gameComplete = save.boardComplete();

                        break;
                    }
                    case 5:
                    {
                        LoadManager loadManager = new LoadManager();
                        ArrayList<String> fileNames = IOTools.getSaveNames();
                        String gameID = loadManager.loadGame(view, fileNames);

                        if (!gameID.isEmpty())
                        {
                            save = game.loadGame(view, gameID);
                            gameComplete = save.boardComplete();
                        }

                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        }
    }
}
