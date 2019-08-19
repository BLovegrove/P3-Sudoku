package FileIO;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class IOTools
{
    /***
     * The directory that stores save text
     */
    public final static File SAVE_DIR = new File("./saves");

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
     * @return An integer representing how many saved boards there are
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

//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public static boolean isNameValid(String fileName)
//    {
//        File file = new File(fileName);
//
//        try
//        {
//            // CHECKS IF ACTUAL SAVED FILE NAME MATCHES ORIGINAL GIVEN ONE
//            boolean isValid = file.getCanonicalFile().getName().equals(fileName);
//            file.delete();
//            return isValid;
//        }
//        catch (IOException e)
//        {
//            return false;
//        }
//    }

    /***
     * Retrieves all the data from a given save file name and creates a saveData instance from it
     * @param fileName The name of the file that needs to be loaded
     * @return A SaveData instance with all the data scanned in from the file
     */
    public static SaveData loadSave(String fileName)
    {
        // INIT SAVE VARIABLES
        int[][] reference = new int[9][9];
        int[][] board = new int[9][9];
        int attempts = 0;

        // INIT EMPTY SAVE
        SaveData saveData = new SaveData(fileName, reference, board, attempts);

        // INIT FILE READER
        BufferedReader reader;

        // START SCANNING DATA FROM FILE
        try
        {
            // GET FILE
            reader = new BufferedReader(new FileReader(SAVE_DIR + "/" + fileName + ".txt"));

            // GET FIRST LINE
            String line = reader.readLine();

            if (line != null)
            {
                // CHECK FOR REFERENCE BOARD FLAG AND TAKE NEXT 9 LINES AS REFERENCE BOARD OBJECT
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

            line = reader.readLine();

            if (line != null)
            {
                // CHECK FOR ACTIVE BOARD FLAG AND TAKE NEXT 9 LINES AS BOARD OBJECT
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
                // CHECK FOR ATTEMPTS FLAG AND TAKE NEXT LINE AS NUMBER OF ATTEMPTS
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

    /***
     * Deletes a given file based on its name if the file is found in the saves folder
     * @param fileName The file name to target for deletion
     * @return Empty string if all went well, otherwise and error message depending on what went wrong
     */
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

    /***
     * Renames a file if it exists and if the new name is a valid file name for the current system
     * @param oldFileName The name of the file to target for renaming
     * @param newFileName The new file name to overwrite current file name
     */
    public static String renameFile(String oldFileName, String newFileName)
    {
        try
        {
            Path target = Paths.get(SAVE_DIR +"/"+ oldFileName +".txt");
            Files.move(target, target.resolveSibling(newFileName));
            return "";
        }
        catch (FileAlreadyExistsException e)
        {
            return "Sorry - That name is taken";
        }
        catch (IOException e)
        {
            return "ERROR: IO EXCEPTION";
        }
    }

    /***
     * Overrides the given save file with the data in the given {@link SaveData} instance
     * @param saveData The save file who's data you want to copy into the save file
     */
    public static void saveFile(SaveData saveData)
    {
        try
        {
            // INIT WRITER FOR GIVEN FILE WITH 'APPEND' FLAG AS FALSE
            PrintWriter writer = new PrintWriter(new FileWriter(SAVE_DIR +"/"+ saveData.boardName +".txt", false));

            // WRITE REFERENCE FLAG
            writer.write("@reference"+ System.lineSeparator());
            int[][] reference = saveData.getReference();

            // WRITE NEXT 9 LINES AS CORRESPONDING REFERENCE BOARD LINES
            for (int i = 0; i < 9; i++)
            {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 9; j++)
                {
                    line.append(reference[i][j]).append(j == 8 ? "" : ",");
                }
                writer.write(line + System.lineSeparator());
            }

            // WRITE BOARD FLAG
            writer.write("@board"+ System.lineSeparator());
            int[][] board = saveData.getBoard();

            // WRITE NEXT 9 LINES AS CORRESPONDING BOARD LINES
            for (int i = 0; i < 9; i++)
            {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 9; j++)
                {
                    line.append(board[i][j]).append(j == 8 ? "" : ",");
                }
                writer.write(line + System.lineSeparator());
            }

            // WRITE ATTEMPTS FLAG AND CORRESPONDING VALUE IN NEXT 2 LINES
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
