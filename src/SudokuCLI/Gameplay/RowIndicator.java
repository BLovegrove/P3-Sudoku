package SudokuCLI.Gameplay;

public enum RowIndicator
{
    A (1), B (2), C (3), D (4), E (5), F (6), G (7), H (8), I (9);

    private final int rowNumber;

    RowIndicator(int rowNumber)
    {
        this.rowNumber = rowNumber;
    }

    public int getValue()
    {
        return this.rowNumber;
    }
}
