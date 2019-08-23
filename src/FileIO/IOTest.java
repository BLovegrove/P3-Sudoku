package FileIO;

import SudokuGen.Generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class IOTest
{
    public static void main(String[] args)
    {
        SaveData saveData;
        saveData = IOTools.loadSave("hardcode_save_01");

        try
        {
            File test = new File(IOTools.SAVE_DIR +"/test.txt");
            boolean fileSaved = test.createNewFile();
            if (!fileSaved)
            {
                System.out.println("Test file creation error: "+ System.lineSeparator() +"File already exists");
            }
        }
        catch (IOException e)
        {
            System.out.println("ERROR: TEST FILE PATH NOT FOUND");
        }

        System.out.println("Number of saves: ");
        System.out.println(IOTools.fileCount());

        System.out.println("File name: ");
        System.out.println("hardcore_save_01");

        System.out.println("Save data board: ");
        int[][] board = saveData.getBoard();
        int[][] reference = saveData.getReference();
        System.out.println(Arrays.toString(board[0]));
        System.out.println("Save data reference: ");
        System.out.println(Arrays.toString(reference[0]));

        System.out.println("Save moves: ");
        System.out.println(saveData.getMoves());

        if (!saveData.getErrorMessage().isEmpty())
        {
            System.out.println("Save error message: ");
            System.out.println(saveData.getErrorMessage());
        }

        String deleteStatus = IOTools.deleteFile("test");
        if (!deleteStatus.isEmpty())
        {
            System.out.println("Deletion error: ");
            System.out.println(deleteStatus);
        }

        System.out.println("File count after deleting test file: ");
        System.out.println(IOTools.fileCount());

<<<<<<< HEAD
        int[][] newBoard = new Generator().generate();
=======

        int[][] newBoard = Generator.generate();
>>>>>>> UnSolver-CLI

        saveData.setBoard(newBoard);
        saveData.setReference(newBoard);

        IOTools.saveFile(saveData);

        saveData = IOTools.loadSave("hardcode_save_01");

        System.out.println("New board after save: ");
        System.out.println(Arrays.toString(saveData.getBoard()[0]));
        System.out.println("New board reference after save: ");
        System.out.println(Arrays.toString(saveData.getReference()[0]));
    }
}
