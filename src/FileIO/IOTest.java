package FileIO;

import java.util.Arrays;

public class IOTest
{
    public static void main(String[] args)
    {
        SaveFile save;
        save = IOTools.loadSave("hardcode_save_01");

        System.out.println("Number of saves: ");
        System.out.println(IOTools.fileCount());

        System.out.println("File name: ");
        System.out.println("hardcore_save_01");

        System.out.println("Save data board: ");
        System.out.println(Arrays.deepToString(save.getBoard()));

        System.out.println("Save attempts: ");
        System.out.println(save.getAttempts());

        if (!save.getErrorMessage().equals(""))
        {
            System.out.println("Save error message: ");
            System.out.println(save.getErrorMessage());
        }

        String deleteStatus = IOTools.deleteFile("hardcode_save_01");
        if (!deleteStatus.equals(""))
        {
            System.out.println("Deletion error: ");
            System.out.println(deleteStatus);
        }

        System.out.println("File count post-delete: ");
        System.out.println(IOTools.fileCount());
    }
}
