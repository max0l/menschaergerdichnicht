import java.awt.*;

public class Spielstein
{
    private Color color;
    private SpielsteinState state;
    private int fieldId;

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public SpielsteinState getState()
    {
        return state;
    }

    public void setState(SpielsteinState state)
    {
        this.state = state;
    }

    public int getFieldId()
    {
        return fieldId;
    }

    public void setFieldId(int fieldId)
    {
        this.fieldId = fieldId;
    }
}
