package SudokuRenderer;

import java.util.Formatter;

public class Renderer
{
    private StringBuilder boardGraphic;
    private Formatter boardFormatter;
    private int[][] boardNumbers;
    private String[] longBoardNumbers;

    public Renderer(int[][] boardNumbers)
    {
        this.boardGraphic = new StringBuilder();
        this.boardFormatter = new Formatter(boardGraphic);
        this.boardNumbers = boardNumbers;
        this.longBoardNumbers = new String[81];
    }

    private void formatBoardNumbers()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (this.boardNumbers[i][j] == 0)
                {
                    this.longBoardNumbers[(i * 9) + j] = " ";
                }
                else
                {
                    this.longBoardNumbers[(i * 9) + j] = this.boardNumbers[i][j] + "";
                }
            }
        }
    }

    private void renderBoard()
    {
        formatBoardNumbers();

        this.boardFormatter.format(
                "╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢"+ System.lineSeparator() +
                        "║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║"+ System.lineSeparator() +
                        "╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝", (Object[]) this.longBoardNumbers

        );
    }

    public String drawBoard()
    {
        renderBoard();
        return this.boardGraphic.toString();
    }

    @Override
    public String toString()
    {
        return this.boardGraphic.toString();
    }
}
