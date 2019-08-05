package SudokuBoardGenerator;

public class BoardGenerator
{
    public static void main(String[] args)
    {
        System.out.println("One unique row: ");
        NineWideRow row = new NineWideRow();
        System.out.println(row);

        SudokuGen generator = new SudokuGen();
        generator.generate();

//        System.out.println("Unique columns/rows: ");
//        ColumnsAndRows board = new ColumnsAndRows();
//        System.out.println(board);

        System.out.println("finished");
    }
}
