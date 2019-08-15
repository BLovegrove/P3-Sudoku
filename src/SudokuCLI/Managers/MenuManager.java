package SudokuCLI.Managers;

import SudokuRenderer.Startup.MainMenu;
import SudokuRenderer.ViewRenderer;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuManager
{
    public int pickMenuItem(ViewRenderer view)
    {
        // GENERATE MENU GRAPHIC
        MainMenu menuGraphic = new MainMenu();

        // CHANGE VIEW TO MAIN MENU
        view.setView(menuGraphic.draw());

        // SET UP SCANNER
        Scanner scanner = new Scanner(System.in);


        while (true)
        {
            // (RE)DRAW THE VIEW WINDOW
            view.render();

            // GET USER INPUT
            String response = scanner.nextLine();

            // PROCESS USER INPUT
            if (response.length() == 1 && Pattern.compile("(?<!\\S)\\d(?!\\S)").matcher(response).matches())
            {
                try {
                    int menuItem = Integer.parseInt(response);

                    if (menuItem >= 0 && menuItem <= 5)
                    {
                        return menuItem;
                    }
                    else
                    {
                        System.out.println("IMPLEMENT STATUS WINDOW ASAP - MENU ITEM OUT OF BOUNDS");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("IMPLEMENT STATUS WINDOW ASAP - NON-NUMBER GOT PAST YOUR REGEX");
                }
            }
            else
            {
                System.out.println("IMPLEMENT STATUS WINDOW ASAP - SOMETHING WENT WRONG");
            }
        }
    }
}
