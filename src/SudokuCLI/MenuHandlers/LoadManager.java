package SudokuCLI.MenuHandlers;
import FileIO.*;

import SudokuRenderer.InfoPanes.LoadInfo;
import SudokuRenderer.UtilityPanes.LoadMenu;
import SudokuRenderer.ViewRenderer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/***
 * Manages the user input for the {@link LoadMenu} stage of the SudokuCLI game
 */
public class LoadManager
{
    /***
     * Process commands given based on definitions in the {@link LoadInfo} class
     * @param view The {@link ViewRenderer} being used to draw all the screen graphics
     * @param fileNames An ArrayList of all the file names in the saves folder at the time of instantiation
     * @return The file name that was selected by the method to load from the {@link SudokuRenderer.Startup.MainMenu}
     */
    public String loadGame(ViewRenderer view, ArrayList<String> fileNames)
    {
        // SETUP PANES / DEFAULT VIEW WINDOW
        LoadMenu loadMenu = new LoadMenu(fileNames);
        LoadInfo loadInfo = new LoadInfo();
        view.setPanes(loadMenu.draw(), loadInfo.draw());

        // SET DEFAULT STATUS MESSAGE
        loadInfo.setStatus("Ready to load a game!");

        // SETUP SCANNER FOR USER INPUT
        Scanner scanner = new Scanner(System.in);

        // SET UP DEFAULT RETURN FILE
        String selectedFile = "";

        // INIT LOOP BOOLEAN
        boolean running = true;

        while (running)
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
                    case "back":
                    {
                        selectedFile = "";
                        running = false;
                        break;
                    }
                    case "sort":
                    {
                        loadMenu.updateFiles(IOTools.getSaveNames());
                    }
                    default:
                    {
                        loadInfo.setStatus("Sorry - Unrecognized command");
                        break;
                    }
                }
            }
            else if (response.length == 2)
            {
                switch (response[0].toLowerCase())
                {
                    case "page":
                    {
                        try
                        {
                            int pageNumber = Integer.parseInt(response[1]);

                            if (pageNumber <= 0)
                            {
                                loadInfo.setStatus("Sorry - Page number "+ pageNumber +" too low!");
                            }
                            else if (pageNumber > loadMenu.getMaxPages())
                            {
                                loadInfo.setStatus("Sorry - Page number "+ pageNumber +" too high!");
                            }
                            else
                            {
                                loadInfo.setStatus("Page "+ pageNumber +" loaded!");
                                loadMenu.setPage(pageNumber);
                                view.setPrimaryPane(loadMenu.draw());
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            loadInfo.setStatus("Sorry - That's not a number");
                        }

                        break;
                    }
                    case "load":
                    {
                        try
                        {
                            int saveID = Integer.parseInt(response[1]);
                            int fileID = (9 * (loadMenu.getPage() - 1)) + saveID - 1;
                            if (fileID < 0 || saveID < 1)
                            {
                                loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too low!");
                            }
                            else if (fileID >= fileNames.size() || saveID > 9)
                            {
                                loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too high!");
                            }
                            else
                            {
                                selectedFile = fileNames.get(fileID);
                                running = false;
                            }

                            break;
                        }
                        catch (NumberFormatException e)
                        {
                            loadInfo.setStatus("Sorry - That's not a number");

                            break;
                        }
                    }
                    case "delete":
                    {
                        try
                        {
                            int saveID = Integer.parseInt(response[1]);
                            int fileID = (9 * (loadMenu.getPage() - 1)) + saveID - 1;
                            if (fileID < 0 || saveID < 1)
                            {
                                loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too low!");
                            }
                            else if (fileID >= fileNames.size() || saveID > 9)
                            {
                                loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too high!");
                            }
                            else
                            {
                                String fileName = fileNames.get(fileID);
                                String deleteResponse = IOTools.deleteFile(fileName);

                                if (deleteResponse.equals(""))
                                {
                                    fileNames.remove(fileID);
                                    loadMenu.updateFiles(fileNames);
                                    loadInfo.setStatus(" -! File successfully deleted !- ");
                                    if (loadMenu.getPage() > loadMenu.getMaxPages())
                                    {
                                        loadMenu.setPage(loadMenu.getPage() - 1);
                                    }
                                    view.setPrimaryPane(loadMenu.draw());
                                }
                                else
                                {
                                    loadInfo.setStatus(deleteResponse);
                                }

                                break;
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            loadInfo.setStatus("Sorry - That's not a number");

                            break;
                        }
                    }
                    default:
                    {
                        loadInfo.setStatus("Sorry - Unrecognized command");
                    }
                }
            }
            else if (response[0].toLowerCase().equals("rename"))
            {
                String[] repairedResponse = new String[]{response[0], response[1], ""};

                for (int i = 2; i < response.length; i++)
                {
                    repairedResponse[2] += (i != (response.length - 1) ? response[i] +" " : response[i]);
                }

                try
                {
                    int saveID = Integer.parseInt(response[1]);
                    int fileID = (9 * (loadMenu.getPage() - 1)) + saveID - 1;
                    if (fileID < 0 || saveID < 1)
                    {
                        loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too low!");
                    }
                    else if (fileID >= fileNames.size() || saveID > 9)
                    {
                        loadInfo.setStatus("Sorry - File number "+ (saveID < 999 ? saveID +" " : "") +"is too high!");
                    }
                    else if (response[2].length() <= 32)
                    {
                        if (
                                repairedResponse[2].startsWith("'") &&
                                repairedResponse[2].substring(repairedResponse[2].length() - 1).equals("'")
                        )
                        {
                            String newFileName = repairedResponse[2].substring(1, repairedResponse[2].length() - 1).toLowerCase();

                            if (!newFileName.toLowerCase().equals(fileNames.get(fileID).toLowerCase()))
                            {
                                String renameResponse = IOTools.renameFile(fileNames.get(fileID), newFileName);
                                if (renameResponse.equals(""))
                                {
                                    fileNames.set(fileID, newFileName);
                                    view.setPrimaryPane(loadMenu.draw());
                                }
                                else
                                {
                                    loadInfo.setStatus(renameResponse);
                                }
                            }
                            else
                            {
                                loadInfo.setStatus("File name already exists. Try again");
                            }
                        }
                        else
                        {
                            loadInfo.setStatus("Please surround filename with ''");
                        }
                    }
                    else
                    {
                        loadInfo.setStatus("Sorry - New file name too long");
                    }
                }
                catch (NumberFormatException e)
                {
                    loadInfo.setStatus("Sorry - That's not a number");
                }
            }
            else
            {
                loadInfo.setStatus("Sorry - Unrecognized command");
            }

            view.setSecondaryPane(loadInfo.draw());
        }
        return selectedFile;
    }
}
