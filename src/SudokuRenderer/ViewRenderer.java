package SudokuRenderer;

import java.util.ArrayList;

public class ViewRenderer
{
    private ArrayList<String> view = new ArrayList<>();
//    private String[] currentBoardPane;
//    private String[] currentCommandPane;
//    private String[] currentMenuPane;

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

    public void updateBrd(String[] boardGraphic, String[] commandsGraphic)
    {
        view = new ArrayList<>();
        addToView(boardGraphic);
        addToView(commandsGraphic);
    }

    public void updateMnu(String[] menuGraphic, String[] commandsGraphic)
    {
        view = new ArrayList<>();
        addToView(menuGraphic);
        addToView(commandsGraphic);
    }

    public void draw()
    {
        for (String row : view)
        {
            System.out.println(row);
        }
    }
}
