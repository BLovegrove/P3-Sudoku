package FileIO;

public class SaveFile
{
    private String boardName = "";
    private int[][] board = new int[9][9];
    private int attempts = 0;
    private String errorMessage = "";

    public SaveFile(String boardName, int[][] board, int attempts)
    {
        this.boardName = boardName;
        this.board = board;
        this.attempts = attempts;
    }

    public int[][] getBoard()
    {
        return board;
    }

    public void setBoard(int[][] board)
    {
        this.board = board;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public void setAttempts(int attempts)
    {
        this.attempts = attempts;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void save()
    {
        IOTools.saveFile(this);
    }
}
