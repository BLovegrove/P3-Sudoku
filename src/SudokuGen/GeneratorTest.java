package SudokuGen;

public class GeneratorTest
{
    public static void main(String[] args)
    {
        Generator generator = new Generator();
        System.out.println(generator);
        generator.generate();

        System.out.println("finished");
    }
}
