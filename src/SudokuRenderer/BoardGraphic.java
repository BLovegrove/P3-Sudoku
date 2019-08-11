package SudokuRenderer;

public class BoardGraphic
{
    private int[][] boardNumbers;
    private String[] longBoardNumbers = new String[81];
    private String[] boardLines;

    public BoardGraphic(int[][] boardNumbers)
    {
        this.boardNumbers = boardNumbers;
        this.boardLines = new String[81];
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

        // board size: 39x20 characters
        this.boardLines = String.format(

                "    1   2   3   4   5   6   7   8   9  ," +
                "  ╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗," +
                "A ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "B ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "C ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣," +
                "D ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "E ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "F ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣," +
                "G ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "H ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╟───┼───┼───╫───┼───┼───╫───┼───┼───╢," +
                "I ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║ %1s │ %1s │ %1s ║," +
                "  ╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝," ,
                (Object[]) this.longBoardNumbers

        ).split(",");
    }

    public String[] draw()
    {
        renderBoard();
        return this.boardLines;
    }
}
