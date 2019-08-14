package SudokuRenderer;

/***
 * contains all necessary methods to create a 2D ASCII-based graphic of a sudoku board with grid lines and guides<br>
 * Board created is 39 characters long and 20 lines deep
 */
public class BoardPane
{
    /***
     * The 2D array representing the sudoku board
     */
    private int[][] boardNumbers;
    /***
     * A 1D array converted from the boardNumbers array and numbers converted into strings<br>
     * (generated by appending each next row to the last)
     */
    private String[] longBoardNumbers = new String[81];
    /***
     * Each individual line of the pane to be used in a {@link SudokuRenderer.ViewRenderer}
     */
    private String[] boardLines;

    /***
     * Standard constructor to initialise the boardLines array and set the boardNumbers array
     * @param boardNumbers a 2D int array representing a sudoku board
     */
    public BoardPane(int[][] boardNumbers)
    {
        this.boardNumbers = boardNumbers;
        this.boardLines = new String[81];
    }

    /***
     * Converts the 2D int array into a 1D array of strings where each individual number is a separate string and<br>
     * any zeroes are turned into an empty space via iteration.
     */
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

    /***
     * Pieces together the ASCII sudoku board image and inserts correct numbers into the grid
     */
    private void renderBoard()
    {
        formatBoardNumbers();

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

    /***
     * triggers the (re)creation of the sudoku board
     * @return The resulting sudoku ASCII graphic in String[] format for use in a {@link ViewRenderer}
     */
    public String[] draw()
    {
        renderBoard();
        return this.boardLines;
    }
}