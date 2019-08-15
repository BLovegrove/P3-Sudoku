package SudokuRenderer;

import SudokuGen.Generator;
import SudokuRenderer.InfoPanes.GameInfo;
import SudokuRenderer.InfoPanes.LoadInfo;
import SudokuRenderer.Startup.MainMenu;
import SudokuRenderer.Startup.ViewCalibrator;
import SudokuRenderer.UtilityPanes.GameBoard;
import SudokuRenderer.UtilityPanes.LoadMenu;

public class RenderTest
{
    public static void main(String[] args)
    {
        String[] TEST_FILES = new String[]{
                "THis is a test 1",
                "THis is a test 2",
                "THis is a test 3",
                "THis is a test 4",
                "THis is a test 5",
                "THis is a test 6",
                "THis is a test 7",
                "THis is a test 8",
                "THis is a test 9",
                "THis is a test 10",
                "THis is a test 11",
                "THis is a test 12",
                "THis is a test 13",
                "THis is a test 14",
                "THis is a test 15",
                "THis is a test 16",
                "THis is a test 17",
                "THis is a test 18",
                "THis is a test 19",
                "THis is a test 20",
                "THis is a test 21",
                "THis is a test 22"
        };

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
