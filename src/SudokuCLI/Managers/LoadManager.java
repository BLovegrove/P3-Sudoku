package SudokuCLI.Managers;

import SudokuRenderer.InfoPanes.LoadInfo;
import SudokuRenderer.UtilityPanes.LoadMenu;
import SudokuRenderer.ViewRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LoadManager
{
    public String loadGame(ViewRenderer view, ArrayList<String> fileNames)
    {
        // SETUP PANES
        LoadMenu loadMenu = new LoadMenu(fileNames);
        LoadInfo loadInfo = new LoadInfo();

        // SET DEFAULT STATUS MESSAGE
        loadInfo.setStatus("Ready to load a game!");

        // SETUP SCANNER FOR USER INPUT
        Scanner scanner = new Scanner(System.in);

        // SET UP DEFAULT RETURN FILE
        String selectedFile = "";

        // START LOOP RUNNING BOOLEAN
        boolean running  = true;


        while (running)
        {
            // DRAW VIEW WINDOW
            view.setPanes(loadMenu.draw(), loadInfo.draw());
            view.render();

            // GET USER INPUT
            String[] response = scanner.nextLine().split(" ");

            // PROCESS USER INPUT
            if (response.length == 1 && response[0].toLowerCase().equals("back"))
            {
                selectedFile = "";
                running = false;
            }
            else if (response.length == 2)
            {
                switch (response[0].toLowerCase())
                {
                    case "page":
                    {
                        if (Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(response[1]).matches())
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
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                loadInfo.setStatus("Sorry - That's not a number");
                            }
                        }
                        else
                        {
                            loadInfo.setStatus("Sorry - no such page number");
                        }
                        break;
                    }
                    case "load":
                    {
                        if (Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(response[1]).matches())
                        {
                            try
                            {
                                int saveID = Integer.parseInt(response[1]);
                                int fileID = (9 * (loadMenu.getPage() - 1)) + saveID - 1;
                                if (fileID < 0)
                                {
                                    loadInfo.setStatus("Sorry - File number "+ saveID +" is too low!");
                                }
                                else if (fileID >= fileNames.size())
                                {
                                    loadInfo.setStatus("Sorry - File number "+ saveID +" is too high!");
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
                        else
                        {
                            loadInfo.setStatus("Sorry - No such save number");
                            break;
                        }
                    }
                    case "delete":
                    {
                        if (Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(response[1]).matches())
                        {
                            try
                            {
                                int saveID = Integer.parseInt(response[1]);
                                int fileID = (9 * (loadMenu.getPage() - 1)) + saveID - 1;
                                if (fileID < 0)
                                {
                                    loadInfo.setStatus("Sorry - File number "+ saveID +" is too low!");
                                }
                                else if (fileID >= fileNames.size())
                                {
                                    loadInfo.setStatus("Sorry - File number "+ saveID +" is too high!");
                                }
                                else
                                {
                                    String fileName = fileNames.get(fileID);
                                    fileNames.remove(fileID);
                                    // FILE IO CLASS : DELETE SAVE FILE WITH SPECIFIED NAME
                                    loadInfo.setStatus(" -! File moved to trash !- ");
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                loadInfo.setStatus("Sorry - That's not a number");
                            }
                        }
                        else
                        {
                            loadInfo.setStatus("Sorry - No such save number");
                        }
                        break;
                    }
                    default:
                    {
                        loadInfo.setStatus("Sorry - unrecognized command");
                    }
                }
            }
            else if (response[0].toLowerCase().equals("rename"))
            {
                String[] repairedResponse = new String[]{response[0], response[1], ""};
                for (int i = 2; i < response.length; i++)
                {
                    repairedResponse[2] += (i != (response.length-1) ? response[i]+" " : response[i]);
                }
                if (Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(repairedResponse[1]).matches())
                {
                    try
                    {
                        int saveID = Integer.parseInt(response[1]);
                        int fileID = (9 * (loadMenu.getPage() - 1)) + saveID;
                        if (fileID < 0)
                        {
                            loadInfo.setStatus("Sorry - File number "+ saveID +" is too low!");
                        }
                        else if (fileID >= fileNames.size())
                        {
                            loadInfo.setStatus("Sorry - File number "+ saveID +" is too high!");
                        }
                        else
                        {
                            if (response[2].length() <= 32)
                            {
                                if (repairedResponse[2].chars().filter(num -> num == '\'').count() == 2)
                                {
                                    // FILE IO CLASS : RENAME METHOD
                                    String newFileName = repairedResponse[2].substring(1, repairedResponse[2].length()-1);
                                    File newFile = new File();
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
                    }
                    catch (NumberFormatException e)
                    {
                        loadInfo.setStatus("Sorry - That's not a number");
                    }
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
