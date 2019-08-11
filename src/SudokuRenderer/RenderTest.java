package SudokuRenderer;

import SudokuGen.Generator;

public class RenderTest
{
    public static void main(String[] args)
    {
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();
        BoardGraphic sudokuRenderer = new BoardGraphic(board);
        MenuGraphic sudokuMenu = new MenuGraphic();
        ViewRenderer view = new ViewRenderer(sudokuRenderer.draw(), sudokuMenu.draw("Test Message"));
        view.draw();
    }
}
