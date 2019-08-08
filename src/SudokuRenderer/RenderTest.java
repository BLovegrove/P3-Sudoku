package SudokuRenderer;

import SudokuGen.Generator;

public class RenderTest
{
    public static void main(String[] args)
    {
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();
        Renderer SudokuRenderer = new Renderer(board);
        System.out.println(SudokuRenderer.drawBoard());
    }
}
