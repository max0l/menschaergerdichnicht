import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Teams
{
    private List<Color> teamColors = new ArrayList<Color>();
    private List<Team> teams = new ArrayList<Team>();

    public List<Team> getTeams()
    {
        return teams;
    }

    public void setTeams(List<Team> teams)
    {
        this.teams = teams;
    }

    public List<Color> getTeamColors()
    {
        return teamColors;
    }

    public void setTeamColors(List<Color> teamColors)
    {
        this.teamColors = teamColors;
    }
}
