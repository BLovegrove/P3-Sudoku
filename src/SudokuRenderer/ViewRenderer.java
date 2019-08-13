package SudokuRenderer;

import java.util.ArrayList;

public class ViewRenderer
{
    private String[] view = new String[20];
    private String[] primaryPane = new String[20];
    private String[] secondaryPane = new String[20];

    private void update()
    {
        for (int i = 0; i < 20; i++)
        {
            this.view[i] = this.primaryPane[i] + this.secondaryPane[i];
        }
    }

    public void setPrimaryPane(String[] pane)
    {
        this.primaryPane = pane;
        update();
    }

    public void setSecondaryPane(String[] pane)
    {
        this.secondaryPane = pane;
        update();
    }

    public void setPanes(String[] primaryPane, String[] secondaryPane)
    {
        this.primaryPane = primaryPane;
        this.secondaryPane = secondaryPane;
        update();
    }

    public void setView(String[] fullScreenPane)
    {
        this.view = fullScreenPane;
    }

    public String[] draw()
    {
        return this.view;
    }

    public void render()
    {
        for (String line : this.view)
        {
            System.out.println(line);
        }
    }
}
