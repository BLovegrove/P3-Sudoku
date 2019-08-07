package SudokuGen;

public class Testing
{
    public static void main(String[] args)
    {
        System.out.println("One unique row: ");
        NineWideRow row = new NineWideRow();
        System.out.println(row);

        Generator generator = new Generator();
        System.out.println(generator);

        System.out.println("finished");
    }
}
