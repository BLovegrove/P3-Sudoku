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
import java.util.Scanner;

public class GameManager
{
    public boolean newGame(ViewRenderer view, DifficultyLevel difficulty)
    {
        // SETUP USER INPUT FOR GAME NAME
        Scanner scanner = new Scanner(System.in);

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
                    File newSaveFile = new File(IOTools.SAVE_DIR, response.toLowerCase() +".txt");
                    if (newSaveFile.createNewFile())
                    {
                        // GENERATE NEW BOARD
                        int[][] reference = new Generator().generate();
                        int[][] board = new int[9][9]; // RUN UNSOLVER HERE

                        // SAVE GENERATED BOARD DATA
                        SaveData newSaveData = new SaveData(response, reference, board , 0, difficulty);
                        newSaveData.save();

                        return loadGame(view, response.toLowerCase());
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

            // (RE)DRAW VIEW WINDOW
            view.render();

            // GET USER INPUT
            String[] response = scanner.nextLine().toLowerCase().split(" ");

            // PROCESS USER INPUT
            switch (response.length)
            {
                case 1:
                {
                    switch (response[0])
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

                            view.render();

                            String restartGame = scanner.nextLine().toLowerCase();

                            if (restartGame.equals("y"))
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

                            view.render();

                            String saveGame = scanner.nextLine().toLowerCase();

                            if (saveGame.equals("y"))
                            {
                                return gameData.boardComplete();
                            }
                            else
                            {
                                gameData.save();
                                return gameData.boardComplete();
                            }
                        }
                        case "fill":
                        {
                            gameInfo.setStatus("Usage: Fill (A-I)(1-9)");

                            break;
                        }
                        case "clear":
                        {
                            gameInfo.setStatus("Usage: Clear (A-I)(1-9)");

                            break;
                        }
                        default:
                        {
                            gameInfo.setStatus("Sorry - Unrecognised command");

                            break;
                        }
                    }

                    break;
                }
                case 2:
                {
                    if (response[1].length() != 2)
                    {
                        gameInfo.setStatus("No such cell. Format: (A-I)(1-9)");

                        break;
                    }
                    else
                    {

                        int[] coordinate = AlphaNumToRowCol(response[1], gameInfo);

                        if (coordinate[0] == -1)
                        {
                            gameInfo.setStatus("Unknown row letter. Use A->I");
                        }
                        else if ( coordinate[1] == -1)
                        {
                            gameInfo.setStatus("Unknown column number. Use 1->9");
                        }
                        else if (response[0].equals("fill"))
                        {
                            gameInfo.setStatus("Choose number to place at "+ response[1].toUpperCase() +" (1->9)");

                            int row = coordinate[0];
                            int col = coordinate[1];

                            gameData.setCellValue(row, col, -1);

                            view.setPanes(gameBoard.draw(), gameInfo.draw());

                            while (true)
                            {
                                view.render();

                                String cellFillResponse = cellFillHandler(row, col, gameData, scanner);

                                try
                                {
                                    int cellValue = Integer.parseInt(cellFillResponse);
                                    moveHistory.add(new int[]{row, col, gameData.getBoard()[row][col]});

                                    gameData.setCellValue(row, col, cellValue);
                                    gameData.addMove();

                                    gameInfo.setStatus("Cell "+ response[1].toUpperCase() +" set to "+ cellValue);

                                    break;
                                }
                                catch (NumberFormatException e)
                                {
                                    if (cellFillResponse.equals("cancel"))
                                    {
                                        gameInfo.setStatus("Fill aborted");

                                        break;
                                    }
                                    else if (cellFillResponse.startsWith("invalid"))
                                    {
                                        char failedCellValue = cellFillResponse.charAt(cellFillResponse.length() - 1);
                                        gameInfo.setStatus("Value "+ failedCellValue  +" is invalid! Try again");
                                    }
                                    else
                                    {
                                        gameInfo.setStatus(cellFillResponse);
                                    }

                                    view.setSecondaryPane(gameInfo.draw());
                                }
                            }
                        }
                        else if (response[0].equals("clear"))
                        {
                            int row = coordinate[0];
                            int col = coordinate[1];

                            moveHistory.add(new int[]{row, col, gameData.getBoard()[row][col]});

                            gameData.setCellValue(row, col, 0);
                            gameData.addMove();

                            gameInfo.setStatus("Cell "+ response[1].toUpperCase() +" cleared.");
                        }
                        else
                        {
                            gameInfo.setStatus("Sorry - Unrecognized command");
                        }
                    }

                    break;
                }
                default:
                {
                    gameInfo.setStatus("Sorry - Unrecognized command");
                }
            }

            view.setPanes(gameBoard.draw(), gameInfo.draw());
        }
    }

    private int[] AlphaNumToRowCol(String alphaNumCoordinate, GameInfo infoPane)
    {
        int[] coordinate = new int[]{-1, -1};

        String rowLabel = ""+ alphaNumCoordinate.toUpperCase().charAt(0);
        try
        {
            int rowValue = RowIndicator.valueOf(rowLabel).getValue();
            if (rowValue > 0 && rowValue < 10)
            {
                coordinate[0] = rowValue - 1;
            }
            else
            {
                infoPane.setStatus("Sorry - Row number not 1 to 9");
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
                coordinate[1] = colValue - 1;

            }
            else
            {
                infoPane.setStatus("Sorry - Column number not 1 to 9");
            }
        }
        catch (NumberFormatException e)
        {
            infoPane.setStatus("Sorry - Column "+ columnLabel +" not found");
        }

        return coordinate;
    }

    private String cellFillHandler(int row, int col, SaveData save, Scanner scanner)
    {

        // GET CURRENT CELL VALUE BACKUP
        int cellValueBackup = save.getBoard()[row][col];

        String fillResponse = scanner.nextLine().toLowerCase();

        if (fillResponse.equals("c"))
        {
            save.setCellValue(row, col, cellValueBackup);

            return "cancel";
        }
        else
        {
            try
            {
                int cellValue = Integer.parseInt(fillResponse);

                if (cellValue < 1 || cellValue > 9)
                {
                    return "Value must from 1 to 9 - Try again";
                }
                else
                {
                    boolean cellValid = SudokuTools.cellValid(
                            row,
                            col,
                            cellValue,
                            save.getBoard()
                    );
                    if (cellValid || save.getDifficulty() == DifficultyLevel.LUDICROUS)
                    {
                        save.setCellValue(row, col, cellValueBackup);
                        return cellValue + "";
                    }
                    else
                    {
                        return "invalid"+ cellValue;
                    }
                }
            }
            catch (NumberFormatException e)
            {
                return "Cell value not a number - Try again";
            }
        }
    }
}
