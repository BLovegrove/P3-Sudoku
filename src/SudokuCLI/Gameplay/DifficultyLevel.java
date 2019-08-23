package SudokuCLI.Gameplay;

/***
 * These are the terms we use to start games with differing difficulty levels - each of "EASY", "MEDIUM' & "HARD"
 * refers to the number of cells that are removed at the start of the game, and "LUDICROUS" produces a "HARD" board
 * that does not tell you whether your input was correct.
 */
public enum DifficultyLevel
{
    NONE,
    EASY,
    MEDIUM,
    HARD,
    LUDICROUS
}
