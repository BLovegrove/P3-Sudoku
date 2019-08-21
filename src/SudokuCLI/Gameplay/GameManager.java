package SudokuCLI.Gameplay;

import FileIO.IOTools;
import FileIO.SaveData;
import SudokuCLI.SudokuTools;
import SudokuGen.Generator;
import SudokuRenderer.InfoPanes.GameInfo;
import SudokuRenderer.UtilityPanes.GameBoard;
import SudokuRenderer.ViewRenderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager
{
    public boolean newGame(ViewRenderer view, DifficultyLevel difficulty)
    {
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
                        int[][] reference = new Generator().generate();
                        int[][] board = new int[9][9]; // RUN UNSOLVER HERE

                        // SAVE BOARD DATA
                        newSave = response;
                        SaveData newSaveData = new SaveData(response, reference, board , 0, difficulty);
                        newSaveData.save();

                        break;
                    }
                    else // FILE EXISTS
                    {
                        System.out.println("Sorry - that save name already exists"+ System.lineSeparator());
                    }
                }
                catch (IOException e) // FILE NAME NOT VALID FOR CURRENT SYSTEM
                {
                    System.out.println("Sorry - That file name is invalid"+ System.lineSeparator());
                }
            }
            else // FILE NAME EXCEEDS 32 CHARACTERS
            {
                System.out.println("Sorry - file name too long"+ System.lineSeparator());
            }
        }

        return loadGame(view, newSave);
    }

    public boolean loadGame(ViewRenderer view, String saveName)
    {

        // GET SAVE FILE FOR GAME
        SaveData gameData = IOTools.loadSave(saveName);

        // INIT PANELS
        GameBoard gameBoard = new GameBoard(gameData.getBoard());
        GameInfo gameInfo = new GameInfo(saveName);

        // SET DEFAULT STATUS MESSAGE
        gameInfo.setStatus("Board loaded. Ready to play!");

        // INIT VIEW WINDOW
        view.setPanes(gameBoard.draw(), gameInfo.draw());


        // INIT USER INPUT
        Scanner scanner = new Scanner(System.in);

        // INIT LAST MOVE DATA
        // Data will be : [0] = cell row, [1] = cell column, [2] = cells last value
        ArrayList<int[]> moveHistory = new ArrayList<>();

        while (true)
        {
            // CHECK IF THE GAME IS COMPLETE
            if (gameData.boardComplete())
            {
                return true;
            }

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
                        if (moveHistory.size() > 0)
                        {
                            int[] move = moveHistory.get(moveHistory.size() - 1);
                            moveHistory.remove(moveHistory.size() - 1);

                            gameData.setCellValue(move[0], move[1], move[2]);
                            gameInfo.setStatus("Move undone!");
                            gameData.addMove();
                        }
                        else
                        {
                            gameInfo.setStatus("No moves left to undo!");
                        }

                        break;
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
                            gameData.setReference(new Generator().generate());
                            gameData.setBoard(new int[9][9]); // RUN UNSOLVER ON NEWBOARD AND PUT IT IN HERE
                            gameData.setMoves(0);
                            gameBoard.update(gameData.getBoard());
                            moveHistory.clear();
                            gameInfo.setStatus("Game reset! Good luck");
                        }
                        else
                        {
                            gameInfo.setStatus("Restart aborted");
                        }

                        break;
                    }
                    case "save":
                    {
                        gameData.save();
                        gameInfo.setStatus("Game saved!");

                        break;
                    }
                    case "exit":
                    {
                        gameInfo.setStatus("Any key to save, N to skip saving");
                        view.setSecondaryPane(gameInfo.draw());

                        // WIPE SCREEN HERE

                        view.render();

                        String saveGame = scanner.nextLine();

                        if (saveGame.toLowerCase().equals("y"))
                        {
                            return gameData.boardComplete();
                        }
                        else
                        {
                            gameData.save();
                            return gameData.boardComplete();
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
                if (response[0].toLowerCase().equals("fill") || response[0].toLowerCase().equals("clear"))
                {
                    if (response[1].length() == 2)
                    {
                        switch (response[0].toLowerCase())
                        {
                            case "fill":
                            {
                                int[] coordinate = AlphaNumToRowCol(response[1].toUpperCase(), gameInfo);

                                if (coordinate[0] > 0 && coordinate[1] > 0)
                                {
                                    gameInfo.setStatus("Choose number to place at "+ response[1].toUpperCase() +" (1->9)");
                                    view.setSecondaryPane(gameInfo.draw());

                                    int row = coordinate[0] - 1;
                                    int col = coordinate[1] - 1;

                                    while (true)
                                    {
                                        // WIPE SCREEN HERE
                                        view.render();

                                        String fillResponse = scanner.nextLine();
                                        try
                                        {
                                            int cellValue = Integer.parseInt(fillResponse);
                                            if (cellValue > 0 && cellValue < 10)
                                            {
                                                boolean cellValid = SudokuTools.cellValid(
                                                        row,
                                                        col,
                                                        cellValue,
                                                        gameData.getBoard()
                                                );
                                                if (cellValid)
                                                {
                                                    moveHistory.add(
                                                            new int[]{ row, col, gameData.getBoard()[row][col] }
                                                    );
                                                    gameData.setCellValue(row, col, cellValue);
                                                    gameData.addMove();
                                                    gameInfo.setStatus("Value "+ cellValue +" Added to cell "+ response[1].toUpperCase());

                                                    break;
                                                }
                                                else
                                                {
                                                    if (gameData.getDifficulty() != DifficultyLevel.LUDICROUS)
                                                    {
                                                        gameInfo.setStatus("Value "+ cellValue +" can't go there! Try again");
                                                        break;
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                gameInfo.setStatus("Sorry - Value must from 1 to 9");
                                            }
                                        }
                                        catch (NumberFormatException e)
                                        {
                                            gameInfo.setStatus("Sorry - Cell value not a number");
                                        }

                                        view.setSecondaryPane(gameInfo.draw());
                                    }
                                }

                                break;
                            }
                            case "clear":
                            {
                                int[] coordinate = AlphaNumToRowCol(response[1].toUpperCase(), gameInfo);

                                if (coordinate[0] > 0 && coordinate[1] > 0)
                                {
                                    int row = coordinate[0] - 1;
                                    int col = coordinate[1] - 1;
                                    gameData.setCellValue(row, col, 0);
                                    gameInfo.setStatus("Cell "+ response[1].toUpperCase() +" cleared.");
                                    gameData.addMove();
                                }

                                break;
                            }
                        }
                    }
                    else
                    {
                        gameInfo.setStatus("No such cell. Format: (A-I)(1-9)");
                    }
                }
                else
                {
                    gameInfo.setStatus("Sorry - Unrecognized command");
                }
            }
            else
            {
                gameInfo.setStatus("Sorry - Unrecognized command");
            }

            view.setPanes(gameBoard.draw(), gameInfo.draw());
        }
    }

    private int[] AlphaNumToRowCol(String alphaNumCoordinate, GameInfo infoPane)
    {
        int[] coordinate = new int[2];

        String rowLabel = ""+ alphaNumCoordinate.charAt(0);
        try
        {
            int rowValue = RowIndicator.valueOf(rowLabel).getValue();
            if (rowValue > 0 && rowValue < 10)
            {
                coordinate[0] = rowValue;
            }
            else
            {
                infoPane.setStatus("Sorry - Row number not 1->9");
            }
        }
        catch (IllegalArgumentException e)
        {
            infoPane.setStatus("Sorry - Row "+ rowLabel +" not found");
        }

        String columnLabel = ""+ alphaNumCoordinate.charAt(1);
        try
        {
            int colValue = Integer.parseInt(columnLabel);
            if (colValue > 0 && colValue < 10)
            {
                coordinate[1] = colValue;

            }
            else
            {
                infoPane.setStatus("Sorry - Column number not 1->9");
            }
        }
        catch (NumberFormatException e)
        {
            infoPane.setStatus("Sorry - Column "+ columnLabel +" not found");
        }

        return coordinate;
    }
}
