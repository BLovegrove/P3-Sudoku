package SudokuCLI.GameplayHandler;

import FileIO.IOTools;
import FileIO.SaveData;
import SudokuCLI.SudokuTools;
import SudokuGen.Generator;
import SudokuRenderer.InfoPanes.GameInfo;
import SudokuRenderer.Startup.NewGame;
import SudokuRenderer.UtilityPanes.GameBoard;
import SudokuRenderer.ViewRenderer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameManager
{
    public boolean newGame(ViewRenderer view, DifficultyLevel difficulty)
    {
        // WIPE SCREEN HERE

        // SETUP USER INPUT FOR GAME NAME
        Scanner scanner = new Scanner(System.in);

        // INIT THE SAVE NAME THAT WILL BE LOADED AFTER ITS CREATED
        String newSave = "";

        while (true)
        {
            // GET USER RESPONSE FOR SAVE NAME
            System.out.println("Please choose a name for your game (32 characters max): ");
            String response = scanner.nextLine();

            // PROCESS USER RESPONSE
            if (response.length() <= 32)
            {
                try
                {
                    // ATTEMPT TO CREATE NEW FILE
                    File newSaveFile = new File(IOTools.SAVE_DIR, response +".txt");
                    if (newSaveFile.createNewFile())
                    {
                        // GENERATE NEW BOARD
                        Generator generator = new Generator();
                        int[][] reference = generator.getBoard();
                        int[][] board = new int[9][9]; // RUN UNSOLVER HERE

                        // SAVE BOARD DATA
                        newSave = response;
                        SaveData newSaveData = new SaveData(response, reference, board , 0);
                        newSaveData.save();

                        break;
                    }
                    else
                    {
                        System.out.println("Sorry - that file exists already"+ System.lineSeparator());
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Sorry - That file name is invalid"+ System.lineSeparator());
                }
            }
            else
            {
                System.out.println("Sorry - file name too long"+ System.lineSeparator());
            }
        }

        return loadGame(view, newSave);
    }

    public boolean loadGame(ViewRenderer view, String saveName)
    {
        // GET SAVE FILE FOR GAME
        SaveData saveData = IOTools.loadSave(saveName);

        // INIT PANELS / DEFAULT VIEW WINDOW
        GameBoard gameBoard = new GameBoard(saveData.getBoard());
        GameInfo gameInfo = new GameInfo(saveName);
        view.setPanes(gameBoard.draw(), gameInfo.draw());

        // SET DEFAULT STATUS MESSAGE
        gameInfo.setStatus("Board loaded. Ready to play!");

        // INIT USER INPUT
        Scanner scanner = new Scanner(System.in);

        // INIT LOOP BOOLEAN
        boolean running = true;

        while (true)
        {
            // WIPE SCREEN HERE

            // RENDER VIEW WINDOW
            view.render();

            // GET USER INPUT
            String[] response = scanner.nextLine().split(" ");

            // PROCESS USER INPUT
            if (response.length == 1)
            {
                switch (response[0].toLowerCase())
                {
                    case "undo":
                    {

                    }
                    case "redo":
                    {

                    }
                    case "restart":
                    {
                        gameInfo.setStatus("Confirm? (Press Y to restart)");
                        view.setSecondaryPane(gameInfo.draw());

                        // WIPE SCREEN HERE

                        view.render();

                        String restartGame = scanner.nextLine();

                        if (restartGame.toLowerCase().equals("y"))
                        {
                            
                        }
                        else
                        {
                            gameInfo.setStatus("Restart aborted");
                            break;
                        }
                    }
                    case "save":
                    {
                        saveData.save();
                        gameInfo.setStatus("Game saved!");
                    }
                    case "exit":
                    {
                        gameInfo.setStatus("Save game first? (Y/N)");
                        view.setSecondaryPane(gameInfo.draw());

                        // WIPE SCREEN HERE

                        view.render();

                        String saveGame = scanner.nextLine();

                        if (saveGame.toLowerCase().equals("y"))
                        {
                            saveData.save();
                            return SudokuTools.gameComplete(saveData);
                        }
                        else if (saveGame.toLowerCase().equals("n"))
                        {
                            return SudokuTools.gameComplete(saveData);
                        }
                        else
                        {
                            gameInfo.setStatus("Sorry - Unrecognised command");
                            break;
                        }
                    }
                    default:
                    {
                        gameInfo.setStatus("Sorry - Unrecognised command");
                    }
                }
            }
            else if (response.length == 2)
            {

            }
            else
            {

            }

            view.setSecondaryPane(gameInfo.draw());
        }
    }
}
