package SudokuRenderer;

import java.util.ArrayList;

public class ViewRenderer
{
    private ArrayList<String> view = new ArrayList<>();
    private String[] menuPane;
    private String[] boardPane;

    public ViewRenderer(String[] boardString, String[] menuString)
    {
        this.menuPane = menuString;
        this.boardPane = boardString;
    }

    private void addToView(String[] pane)
    {
        for (int i = 0; i < pane.length; i++)
        {
            try
            {
                this.view.set(i, this.view.get(i) + pane[i]);
            }
            catch (IndexOutOfBoundsException e)
            {
                this.view.add(pane[i]);
            }
        }
    }

    public void draw()
    {
        addToView(boardPane);
        addToView(menuPane);

        for (String row : view)
        {
            System.out.println(row);
        }
    }

}
