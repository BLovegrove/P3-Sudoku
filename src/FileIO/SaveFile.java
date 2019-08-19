package FileIO;

public class SaveFile
{
    public String boardName;
    private int[][] reference;
    private int[][] board;
    private int attempts;
    private String errorMessage = "";

    public SaveFile(String boardName,int[][] reference, int[][] board, int attempts)
    {
        this.boardName = boardName;
        this.reference = reference;
        this.board = board;
        this.attempts = attempts;
    }

    public int[][] getReference()
    {
        return reference;
    }

    public void setReference(int[][] reference)
    {
        this.reference = reference;
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
