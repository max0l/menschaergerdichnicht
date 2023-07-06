import java.util.ArrayList;
import java.util.List;

public class Spielfeld
{
    public List<Feld> getFields()
    {
        return fields;
    }

    public void setFields(List<Feld> fields)
    {
        this.fields = fields;
    }

    private List<Feld> fields = new ArrayList<Feld>();
}
