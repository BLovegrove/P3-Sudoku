package SudokuCLI.MenuHandlers;
import FileIO.*;

import SudokuRenderer.InfoPanels.LoadInfo;
import SudokuRenderer.CustomPanels.LoadMenu;
import SudokuRenderer.ViewRenderer;

import java.util.ArrayList;
import java.util.Scanner;

/***
 * Manages the user input for the {@link LoadMenu} stage of the SudokuCLI game
 */
public class LoadManager
{
    /***
     * Process commands given based on definitions in the {@link LoadInfo} class
     * @param view The {@link ViewRenderer} being used to draw all the screen graphics
     * @param fileNames An ArrayList of all the file names in the saves folder at the time of instantiation
     * @return The file name that was selected by the method to load from the {@link SudokuRenderer.CustomPanels.MainMenu}
     */
    public String loadGame(ViewRenderer view, ArrayList<String> fileNames)
    {
        LoadMenu loadMenu = new LoadMenu(fileNames);
        LoadInfo loadInfo = new LoadInfo();

        // SET DEFAULT STATUS MESSAGE
        //CHECK NUMBER OF SAVES ISN'T LUDICROUSLY HIGH
        if (fileNames.size() > 100)
        {
            loadInfo.setStatus("100+ Saves! Check saves folder ASAP");
        }
        else
        {
            loadInfo.setStatus("Ready to load a game!");
        }

        // INIT VIEW WINDOW
        view.setPanels(loadMenu.draw(), loadInfo.draw());

        // SETUP SCANNER FOR USER INPUT
        Scanner scanner = new Scanner(System.in);

        // SET UP DEFAULT RETURN FILE
        String selectedFile = "";

        while (true)
        {

            // RENDER VIEW WINDOW
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
                        case "back":
                        {
                            return "";
                        }
                        case "sort":
                        {
                            loadMenu.updateFiles(IOTools.getSaveNames());
                            fileNames = loadMenu.getFileNames();
                            loadInfo.setStatus("List sorted!");

                            break;
                        }
                        case "load":
                        {
                            loadInfo.setStatus("Usage: Load (1-9)");

                            break;
                        }
                        case "rename":
                        {
                            loadInfo.setStatus("Usage: Rename (1-9)");

                            break;
                        }
                        case "delete":
                        {
                            loadInfo.setStatus("Usage: Delete (1-9)");

                            break;
                        }
                        case "page":
                        {
                            loadInfo.setStatus("Usage: Page (num)");

                            break;
                        }
                        default:
                        {
                            loadInfo.setStatus("Sorry - Unrecognized command");

                            break;
                        }
                    }

                    break;
                }
                case 2:
                {
                    switch (response[0])
                    {
                        case "page":
                        {
                            int pageNumber;

                            try
                            {
                                pageNumber = Integer.parseInt(response[1]);
                            }
                            catch (NumberFormatException e)
                            {
                                loadInfo.setStatus("Sorry - That's not a number");

                                break;
                            }

                            if (pageNumber < 1)
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
                            }

                            break;
                        }
                        case "load":
                        {
                            int[] selection = selectSave(response[1], loadMenu, loadInfo);
                            int fileID = selection[1];

                            if (fileID < 0)
                            {
                                break;
                            }

                            return fileNames.get(fileID);
                        }
                        case "delete":
                        {

                            int[] selection = selectSave(response[1], loadMenu, loadInfo);

                            int saveID = selection[0];
                            int fileID = selection[1];

                            if (fileID < 0)
                            {
                                break;
                            }

                            String saveIDString = (saveID < 999 ? saveID +" " : "");

                            String fileName = fileNames.get(fileID);
                            String deleteResponse = IOTools.deleteFile(fileName);

                            if (!deleteResponse.isEmpty())
                            {
                                loadInfo.setStatus(deleteResponse);
                            }
                            else
                            {
                                fileNames.remove(fileID);
                                loadMenu.updateFiles(fileNames);
                                loadInfo.setStatus("-! File "+ saveIDString +" successfully deleted !-");

                                if (loadMenu.getPage() > loadMenu.getMaxPages() && loadMenu.getPage() != 1)
                                {
                                    loadMenu.setPage(loadMenu.getPage() - 1);
                                }
                            }

                            break;
                        }
                        case "rename":
                        {
                            int[] selection = selectSave(response[1], loadMenu, loadInfo);

                            int saveID = selection[0];
                            int fileID = selection[1];

                            if (fileID < 0)
                            {
                                break;
                            }

                            loadInfo.setStatus("Choose a name for save #"+ saveID);
                            view.setSecondaryPanel(loadInfo.draw());

                            while (true)
                            {
                                // WIPE SCREEN HERE
                                view.render();

                                String newFileName = scanner.nextLine();

                                if (newFileName.length() > 32)
                                {
                                    loadInfo.setStatus("Sorry - Name too long. Try again");
                                }
                                else if (newFileName.equals(fileNames.get(fileID)))
                                {
                                    loadInfo.setStatus("Save name already exists. Try again");
                                }
                                else
                                {
                                    String renameResponse = IOTools.renameFile(fileNames.get(fileID), newFileName);

                                    if (!renameResponse.isEmpty())
                                    {
                                        if (renameResponse.equals("abort"))
                                        {
                                            loadInfo.setStatus("Rename aborted!");
                                            break;
                                        }
                                        else
                                        {
                                            loadInfo.setStatus(renameResponse);
                                        }
                                    }
                                    else
                                    {
                                        fileNames.set(fileID, newFileName);
                                        loadInfo.setStatus("Save #"+ saveID +" renamed!");

                                        break;
                                    }
                                }

                                view.setSecondaryPanel(loadInfo.draw());
                            }

                            break;
                        }
                        default:
                        {
                            loadInfo.setStatus("Sorry - Unrecognized command");
                        }
                    }

                    break;
                }
                default:
                {
                    loadInfo.setStatus("Sorry - Unrecognized command");
                }
            }

            view.setPanels(loadMenu.draw(), loadInfo.draw());
        }
    }

    /**
     * Allows the user to select a save
     * @param input a user input from console
     * @param menu the list of saved games
     * @param info the status & command panel
     * @return the absolute position of the save in question within the fileNames array generated by
     * {@link IOTools#getSaveNames()}
     */
    private int[] selectSave(String input, LoadMenu menu, LoadInfo info)
    {
        try
        {
            int saveID = Integer.parseInt(input);
            int fileID = (9 * (menu.getPage() - 1)) + saveID - 1;

            String saveIDString = (saveID < -99 ? "" : saveID+" ");

            if (saveID < 1)
            {
                info.setStatus("Sorry - Save number "+ saveIDString +"is too low!");
                return new int[]{-1, -1};
            }
            else if (saveID > menu.getFileNames().size())
            {
                info.setStatus("Sorry - Save number "+ saveIDString +"is too high!");
                return new int[]{-1, -1};
            }
            else if (menu.getFileNames().size() == 0)
            {
                info.setStatus("No more saves in list!");
                return new int[]{-1, -1};
            }
            else
            {
                return new int[]{saveID,fileID};
            }
        }
        catch(NumberFormatException e)
        {
            info.setStatus("Sorry - That's not a number");
            return new int[]{-1, -1};
        }
    }
}
