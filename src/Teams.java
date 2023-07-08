import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Teams {
    private List<Team> teams = new ArrayList<Team>();

    public Teams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public boolean checkIfGameIsFinished() {
        for(Team team : teams) {
            if(!team.getIsFinished()) {
                return false;
            }
        }
        return true;
    }

    //Wieso?
}
