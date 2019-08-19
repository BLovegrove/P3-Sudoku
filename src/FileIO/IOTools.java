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
    /***\
     * The directory that stores save text
     */
    public final static File SAVE_DIR;

    static
    {
        SAVE_DIR = new File("./saves");
    }

    /***
     * Retrieves names of all save files in the save folder so the app can keep track of what it can/can't touch
     * @return {@code ArrayList<String>} containing all the names of the currently available save files
     */
    public static ArrayList<String> getSaveNames()
    {
        // INIT BLANK LIST
        ArrayList<String> files = new ArrayList<>();

        // ITERATE TO FILL LIST WITH FILE NAMES
        // requireNonNull added by IDE to stave off warnings
        for (File file : Objects.requireNonNull(SAVE_DIR.listFiles())) {
            files.add(file.getName());
        }

        return files;
    }

    /***
     * Gets the number of files available in the saves folder defined in {@link #SAVE_DIR}.
     * @return An integer representing how many saved boards there are.
     */
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
        int[][] reference = new int[9][9];
        int[][] board = new int[9][9];
        int attempts = 0;

        // INIT EMPTY SAVE
        SaveFile saveData = new SaveFile(fileName, reference, board, attempts);

        // INIT FILE READER
        BufferedReader reader;

        try
        {
            reader = new BufferedReader(new FileReader(SAVE_DIR + "/" + fileName + ".txt"));

            String line = reader.readLine();

            if (line != null)
            {
                if (line.equals("@reference"))
                {
                    for (int i = 0; i < 9; i++)
                    {
                        String[] row = reader.readLine().split(",");
                        for (int j = 0; j < 9; j++)
                        {
                            try
                            {
                                reference[i][j] = Integer.parseInt(row[j]);
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
                    saveData.setErrorMessage("No ref. board found in file!");
                }
            }
            else
            {
                System.out.println("No data found in file!");
            }

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

            saveData.setReference(reference);
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

    public static void saveFile(SaveFile saveData)
    {
        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter(SAVE_DIR +"/"+ saveData.boardName +".txt", false));

            writer.write("@reference"+ System.lineSeparator());
            int[][] reference = saveData.getReference();

            for (int i = 0; i < 9; i++)
            {
                String line = "";
                for (int j = 0; j < 9; j++)
                {
                    line += reference[i][j] + (j == 8 ? "" : ",");
                }
                writer.write(line + System.lineSeparator());
            }

            writer.write("@board"+ System.lineSeparator());
            int[][] board = saveData.getBoard();

            for (int i = 0; i < 9; i++)
            {
                String line = "";
                for (int j = 0; j < 9; j++)
                {
                    line += board[i][j] + (j == 8 ? "" : ",");
                }
                writer.write(line + System.lineSeparator());
            }

            writer.write("@attempts"+ System.lineSeparator());
            writer.write(saveData.getAttempts() +"");

            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
