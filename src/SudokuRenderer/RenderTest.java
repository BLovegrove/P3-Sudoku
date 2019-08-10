package SudokuRenderer;

import SudokuGen.Generator;

public class RenderTest
{
    public static void main(String[] args)
    {
        Generator SudokuGenerator = new Generator();
        int[][] board = SudokuGenerator.getBoard();
        Renderer SudokuRenderer = new Renderer(new int[9][9]);
        System.out.println(SudokuRenderer.drawBoard());
    }
}
