package SudokuCLI.MenuHandlers;

import SudokuRenderer.Startup.MainMenu;
import SudokuRenderer.ViewRenderer;

import java.util.Scanner;
import java.util.regex.Pattern;

/***
 * Manages the user input processing and error reporting for the {@link MainMenu} stage of the SudokuCLI game
 */
public class MenuManager
{
    /***
     * Select an item from the games {@link MainMenu} to execute in the {@link SudokuCLI.Main} handler
     * @param view The {@link ViewRenderer} being used to draw all the screen graphics
     * @param menuErrorMessage The error message (if any) set during the last execution of this method
     * @return Any error encountered during the execution of this method. Otherwise blank string
     */
    public String pickMenuItem(ViewRenderer view, String menuErrorMessage)
    {
        // GENERATE MENU GRAPHIC
        MainMenu menuGraphic = new MainMenu();

        // CHANGE VIEW TO MAIN MENU
        view.setView(menuGraphic.draw());

        // SET UP SCANNER
        Scanner scanner = new Scanner(System.in);

        // (RE)DRAW THE VIEW WINDOW
        view.render();

        // PRINT ERROR IF ONE EXISTS
        if (!menuErrorMessage.equals(""))
        {
            System.out.println(menuErrorMessage+ ". Try again ↴");
        }

        // GET USER INPUT
        String response = scanner.nextLine();

        // PROCESS USER INPUT
        if (response.length() == 1 && Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(response).matches())
        {
            try {
                int menuItem = Integer.parseInt(response);

                if (menuItem >= 0 && menuItem <= 5)
                {
                    return ""+ menuItem;
                }
                else
                {
                    return "Sorry - Menu item "+ menuItem +" not found";
                }
            }
            catch (NumberFormatException e)
            {
                return "FATAL ERROR: NON-NUMBER GOT PAST REGEX";
            }
        }
        else
        {
            return "Sorry - Command not recognised";
        }
    }
}
