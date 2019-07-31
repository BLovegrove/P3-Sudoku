package SudokuBoardGenerator;

import java.util.*;

public class ColumnsAndRows
{
    private int[][] board = new int[9][9];

    public ColumnsAndRows()
    {
        populateBoard();
    }

    private boolean valueInRow(int row, int value)
    {
        for (int i = 0; i < this.board[row].length; i++)
        {
            if (this.board[row][i] == value)
            {
                return true;
            }
        }

        return false;
    }

    private boolean valueInCol(int col, int value)
    {
        for (int i = 0; i < this.board.length; i++)
        {
            if (this.board[i][col] == value)
            {
                return true;
            }
        }

        return false;
    }

    private void populateBoard()
    {
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                int boxValue;

                do {
                    boxValue = SudokuTools.randInt();
                } while (valueInRow(row, boxValue) || valueInCol(col, boxValue));

                this.board[row][col] = boxValue;
            }
        }
    }

    @Override
    public String toString()
    {
        return "" +
               Arrays.toString(this.board[0]) + System.lineSeparator() +
               Arrays.toString(this.board[1]) + System.lineSeparator() +
               Arrays.toString(this.board[2]) + System.lineSeparator() +
               Arrays.toString(this.board[3]) + System.lineSeparator() +
               Arrays.toString(this.board[4]) + System.lineSeparator() +
               Arrays.toString(this.board[5]) + System.lineSeparator() +
               Arrays.toString(this.board[6]) + System.lineSeparator() +
               Arrays.toString(this.board[7]) + System.lineSeparator() +
               Arrays.toString(this.board[8]) + System.lineSeparator();
    }
}
