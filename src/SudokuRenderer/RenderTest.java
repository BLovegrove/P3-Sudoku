package SudokuRenderer;

import SudokuGen.Generator;

public class RenderTest
{
    public static void main(String[] args)
    {
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();
        BoardGraphic BG = new BoardGraphic(board);
        CommandsGraphic CG = new CommandsGraphic("board");
        ViewRenderer view = new ViewRenderer();
        view.updateBrd(BG.draw(), CG.draw("Test Status Message!"));
        view.draw();
    }
}
