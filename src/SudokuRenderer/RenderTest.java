package SudokuRenderer;

import SudokuGen.Generator;
import SudokuRenderer.InfoPanes.GameInfo;
import SudokuRenderer.InfoPanes.LoadInfo;
import SudokuRenderer.Startup.MainMenu;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.UtilityPanes.GameBoard;
import SudokuRenderer.UtilityPanes.LoadMenu;

import java.util.ArrayList;

public class RenderTest
{
    public static void main(String[] args)
    {

        ArrayList<String> TEST_FILES = new ArrayList<>();

        for (int i = 0; i < 25; i++)
        {
            TEST_FILES.set(i, String.format("Test File %1s", i));
        }

        //show calibrate board box
        ViewCalibrator.calibrate(20);

        //new board numbers
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();

        //new board graphic
        GameBoard BP = new GameBoard(board);

        //new info panels
        GameInfo IPP = new GameInfo();
        LoadInfo IPL = new LoadInfo();

        //new load menu
        LoadMenu LMP = new LoadMenu(TEST_FILES);

        //new view window
        ViewRenderer view = new ViewRenderer();

        //render main menu
        MainMenu MM = new MainMenu();
        view.setView(MM.draw());
        view.render();

        //draw basic board
        IPP.setStatus("Test Status Message!");
        view.setPanes(BP.draw(), IPP.draw());
        view.render();

        //draw 'load games' page 1
        IPL.setStatus("BOOP 1");
        view.setPanes(LMP.draw(), IPL.draw());
        view.render();

        //draw board again! but keep new secondary pane
        view.setPrimaryPane(BP.draw());
        view.render();

        //draw 'load games' page 3
        LMP.setPage(3);
        view.setPrimaryPane(LMP.draw());
        view.render();

        //draw 'load games' page 3 again w/ changed status
        IPL.setStatus("BOOP 1 changed!");
        view.setSecondaryPane(IPL.draw());
        view.render();
    }
}
