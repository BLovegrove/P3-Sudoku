package FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class IOTools
{
    private final static File SAVE_DIR = new File("../saves");

    public static ArrayList<String> getSaveNames()
    {
        ArrayList<String> files = new ArrayList<>();

        // requireNonNull added by IDE to stave off warnings
        for (final File file : Objects.requireNonNull(SAVE_DIR.listFiles())) {
            files.add(file.getName());
        }

        return files;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isNameValid(String fileName)
    {
        File file = new File(fileName);

        try
        {
            boolean isValid = file.getCanonicalFile().getName().equals(fileName);
            file.delete();
            return isValid;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    public static SaveFile loadSave(String fileName)
    {
        // INIT SAVE VARIABLES
        int[][] board = new int[9][9];
        int attempts = 0;

        // INIT EMPTY SAVE
        SaveFile saveData = new SaveFile(fileName, board, attempts);

        // INIT FILE READER
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(SAVE_DIR + "/" + fileName + ".txt"));

            String line = reader.readLine();
            switch (line)
            {
                case "@board":
                {
                    for (int i = 0; i < 9; i++)
                    {
                        String[] row = reader.readLine().split(",");
                        for (int j = 0; j < 9; j++)
                        {
                            board[i][j] = Integer.parseInt(row[j]);
                        }
                    }
                }
                case "@attempts":
                {
                    attempts = Integer.parseInt(reader.readLine());
                }
                default:
                {
                    saveData.setErrorMessage("Something went wrong when building the save!");
                }
            }

            saveData.setBoard(board);
        }
        catch (FileNotFoundException e)
        {
            saveData.setErrorMessage("File not found!");
        }
        catch (IOException e)
        {
            saveData.setErrorMessage("File could not be read!");
        }


        return saveData;
    }

    public static void deleteFile(String fileName)
    {

    }

    public static void renameFile(String oldFileName, String newFileName)
    {

    }

    public static void saveFile(SaveFile save)
    {

    }
}
