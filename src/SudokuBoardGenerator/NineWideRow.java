package SudokuBoardGenerator;

public class NineWideRow
{
    private int[] row = new int[9];

    public NineWideRow()
    {
        generateBoardRow(this.row);
    }

    private void generateBoardRow(int[] boardRow)
    {
        for (int i = 0; i < boardRow.length; i++)
        {
            int selectedValue = SudokuTools.randRange(1, 9);

            while (SudokuTools.isValueInRow(boardRow, selectedValue))
            {
                selectedValue = SudokuTools.randRange(1, 9);
            }

            boardRow[i] = selectedValue;
        }
    }

    public int[] getRow()
    {
        return row;
    }

    @Override
    public String toString()
    {
        int[] board = getRow();
        String returnString = "";
        for (int rowNumber : board)
        {
            returnString = returnString + rowNumber + " ";
        }
        return returnString;
    }
}
