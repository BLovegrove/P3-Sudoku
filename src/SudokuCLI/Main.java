package SudokuCLI;

import FileIO.IOTools;
import FileIO.SaveData;
import SudokuCLI.Gameplay.DifficultyLevel;
import SudokuCLI.Gameplay.GameManager;
import SudokuCLI.MenuHandlers.LoadManager;
import SudokuCLI.MenuHandlers.MenuManager;
import SudokuRenderer.InfoPanels.FailureInfo;
import SudokuRenderer.InfoPanels.InfoPanel;
import SudokuRenderer.InfoPanels.SuccessInfo;
import SudokuRenderer.CustomPanels.ViewCalibrator;
import SudokuRenderer.CustomPanels.GameBoard;
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

        boolean running = true;
        while (running)
        {
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
                        SaveData save = game.newGame(view, DifficultyLevel.EASY);
                        gameComplete(view, save);

                        break;
                    }
                    case 2:
                    {
                        SaveData save = game.newGame(view, DifficultyLevel.MEDIUM);
                        gameComplete(view, save);

                        break;
                    }
                    case 3:
                    {
                        SaveData save = game.newGame(view, DifficultyLevel.HARD);
                        gameComplete(view, save);

                        break;
                    }
                    case 4:
                    {
                        SaveData save = game.newGame(view, DifficultyLevel.LUDICROUS);
                        gameComplete(view, save);

                        break;
                    }
                    case 5:
                    {
                        LoadManager loadManager = new LoadManager();
                        ArrayList<String> fileNames = IOTools.getSaveNames();
                        String gameID = loadManager.loadGame(view, fileNames);

                        if (!gameID.isEmpty())
                        {
                            SaveData save = game.loadGame(view, gameID);
                            gameComplete(view, save);
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

    private static void gameComplete(ViewRenderer view, SaveData save)
    {
        if (SudokuTools.boardFull(save.getBoard()))
        {

            GameBoard gameBoard = new GameBoard(save.getBoard());
            InfoPanel resultPanel = null;

            if (save.boardComplete())
            {
                resultPanel= new SuccessInfo(save);
                resultPanel.setStatus("Press any key to go back to the menu");
            }
            else if (save.getDifficulty() == DifficultyLevel.LUDICROUS)
            {
                resultPanel = new FailureInfo(save);
                resultPanel.setStatus("Press any key to go back to the menu");
                IOTools.deleteFile(save.boardName);
            }

            if (resultPanel != null)
            {
                view.setPanels(gameBoard.draw(), resultPanel.draw());
                view.render();

                String resultResponse = new Scanner(System.in).nextLine();
            }
        }
    }
}
