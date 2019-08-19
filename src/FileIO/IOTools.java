package FileIO;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class IOTools
{
    private final static File SAVE_DIR = new File("./saves");

    public static ArrayList<String> getSaveNames()
    {
        ArrayList<String> files = new ArrayList<>();

        // requireNonNull added by IDE to stave off warnings
        for (File file : Objects.requireNonNull(SAVE_DIR.listFiles())) {
            files.add(file.getName());
        }

        return files;
    }

    public static int fileCount()
    {
        int count = 0;

        for (File file : Objects.requireNonNull(SAVE_DIR.listFiles()))
        {
            count += 1;
        }

        return count;
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
        BufferedReader reader;

        try
        {
            reader = new BufferedReader(new FileReader(SAVE_DIR + "/" + fileName + ".txt"));

            String line = reader.readLine();

            if (line != null)
            {
                if (line.equals("@board"))
                {
                    for (int i = 0; i < 9; i++)
                    {
                        String[] row = reader.readLine().split(",");
                        for (int j = 0; j < 9; j++)
                        {
                            try
                            {
                                board[i][j] = Integer.parseInt(row[j]);
                            }
                            catch (NumberFormatException e)
                            {
                                saveData.setErrorMessage("Save corrupt! cell was ! number");
                            }
                        }
                    }
                }
                else
                {
                    saveData.setErrorMessage("No board found in file!");
                }
            }
            else
            {
                System.out.println("No data found in file!");
            }


            line = reader.readLine();
            if (line != null)
            {
                if (line.equals("@attempts"))
                {
                    try
                    {
                        saveData.setAttempts(Integer.parseInt(reader.readLine()));
                    }
                    catch (NumberFormatException e)
                    {
                        saveData.setErrorMessage("Save corrupt! attempts was ! number");
                    }
                }
                else
                {
                    saveData.setErrorMessage("No attempt count found in file!");
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

    public static String deleteFile(String fileName)
    {
        if (new File(SAVE_DIR +"/"+ fileName +".txt").delete())
        {
            return "";
        }
        else
        {
            return "Sorry - File not found!";
        }
    }

    public static void renameFile(String oldFileName, String newFileName)
    {

    }

    public static void saveFile(SaveFile save)
    {

    }
}
