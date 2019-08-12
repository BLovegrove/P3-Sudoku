package SudokuRenderer;

import SudokuGen.Generator;

public class RenderTest
{
    public static void main(String[] args)
    {
        //new board numbers
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();

        //new board graphic
        BoardPane BP = new BoardPane(board);

        //new info panels
        BoardInfoPane IPP = new BoardInfoPane();
        LoadInfoPane IPL = new LoadInfoPane();

        //new load menu
        LoadMenuPane LMP = new LoadMenuPane();

        //new view window
        ViewRenderer view = new ViewRenderer();

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
