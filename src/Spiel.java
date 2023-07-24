import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Spiel {
    private boolean gameIsRunning = true;
    private Random random = new Random();
    private List<Team> teams = new ArrayList<Team>();

    private Spielfeld spielfeld;

    public Spiel() {
        spielfeld = new Spielfeld();
        setupPlayers(spielfeld);
    }

    //TODO: Muss ein spielestein von dem Spawn wegbewegt werden? Oder kann er da erstmal bleiben (vom eigenen Team)
    // solange man keine 6 würfelt?
    void startGame() {
        System.out.println("Spiel gestartet");
        while (gameIsRunning) {
            run();
        }
    }

    void setupPlayers(Spielfeld spielfeld)
    {
        teams.add(new Team(Color.RED, 0, spielfeld));
        teams.add(new Team(Color.BLUE, 10, spielfeld));
        teams.add(new Team(Color.GREEN, 20, spielfeld));
        teams.add(new Team(Color.YELLOW, 30, spielfeld));
    }

    void run() {
        for (Team team : teams) {
            System.out.println("Team " + team.getColor() + " ist am Zug");
            makeAmove(team);
            System.out.println("Team " + team.getColor() + " ist fertig" + "\n\n");

            if(team.getIsFinished())
            {
                System.out.println("Spiel beendet");
                gameIsRunning = false;
                break;
            }
        }
    }

    private void makeAmove(Team team)
    {
        //if all figures are in the spawn
        if(team.checkIfAllPiecesAreInStart())
        {
            tryToGetOutOfSpawn(team);
            return;
        }

        //Normal Playing
        if(!team.getIsFinished())
        {
            play(team);
        }
    }

    private void play(Team team)
    {
        int diceRoll = rollDice();

        if (diceRoll == 6)
        {
            selectPiece(team, diceRoll);
            play(team);
        }
        else
        {
            selectPiece(team, diceRoll);
        }
    }

    private void selectPiece(Team team, int diceRoll) {
        List<Spielstein> movableSpielsteine = team.getMovableSpielsteine(diceRoll);

        removePiecesThatWouldOverRun(movableSpielsteine, team, diceRoll);
        removePiecesThatWouldLandOnOwnPiece(team, movableSpielsteine, diceRoll);


        if (movableSpielsteine.size() == 1) {
            moveSpielstein(movableSpielsteine.get(0), diceRoll, team);
            return;
        }

        //This else if case checks if there is a piece in the spawn and if there is one, it will be moved to the field
        else if(diceRoll == 6 && checkIfSpawnIsOccupied(team)) {
            Optional<Spielstein> mustBeMoved = movableSpielsteine.stream().filter(spielstein ->
                    spielstein.getFieldId() == team.getStartField()).findFirst();
            mustBeMoved.ifPresent(spielstein -> moveSpielstein(spielstein, diceRoll, team));
            return;
        }
        //This else if case checks if there is a piece in home and if there is one, it should be used
        else if (diceRoll == 6 && (movableSpielsteine.stream().anyMatch(spielstein -> spielstein.getState() == SpielsteinState.STATE_HOME))) {
            movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_PLAYING);

        } else if (checkIfPieceCanKickOtherPiece(team, diceRoll, movableSpielsteine)) {
            System.out.println("Es kann eine andere Spielfigur geschlagen werden");
            System.out.println("movableSpielsteine: " + movableSpielsteine.size());
        }
        if(movableSpielsteine.isEmpty()) {
            System.out.println("Keine Spielfiguren können bewegt werden");
            return;
        }
        moveSpielstein(movableSpielsteine.get(random.nextInt(movableSpielsteine.size())), diceRoll, team);
    }

    private void removePiecesThatWouldOverRun(List<Spielstein> movableSpielsteine, Team team, int diceRoll) {
        movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_PLAYING
                && spielstein.getWalkedFields() + diceRoll >= 44 );

        movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_FINISH
                && diceRoll + team.getSpielFeldIntOfSpielsteinInFinish(spielstein) > 4);
    }

    private void removePiecesThatWouldLandOnOwnPiece(Team team, List<Spielstein> movableSpielsteine, int diceRoll) {
        movableSpielsteine.removeIf(spielstein -> {
            Feld nextFeld = spielfeld.getFeld((spielstein.getFieldId() + diceRoll) % 40);
            return spielstein.getState() == SpielsteinState.STATE_PLAYING
                    && nextFeld.getIsOccupied()
                    && nextFeld.getOccupier().getColor() == team.getColor();
        });

        //TODO:Also needs to remove pieces that are in goal or would go to the goal
        //TODO: Spielstein sollte nachdem es im Ziel ist die SpielFeldID des momentanen Feldes im Ziel bekommen
        //Figure is already in goal
        movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_FINISH
                && team.getIsOccupiedInFinish(diceRoll + team.getSpielFeldIntOfSpielsteinInFinish(spielstein) - 1));
        //Figure is still on the field
        movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_PLAYING
                && (spielstein.getWalkedFields() + diceRoll) >= 40
                && team.getIsOccupiedInFinish(spielstein.getWalkedFields() + diceRoll - 40));
    }

    private boolean checkIfSpawnIsOccupied(Team team) {
        Feld spawn = spielfeld.getFeld(team.getStartField());
        return spawn.getIsOccupied() && spawn.getOccupier().getColor() == team.getColor();
    }

    private boolean checkIfPieceCanKickOtherPiece(Team team, int diceRoll, List<Spielstein> movableSpielsteine) {
        int oldSize = movableSpielsteine.size();
        movableSpielsteine.removeIf(spielstein -> {
            Feld nextFeld = spielfeld.getFeld((spielstein.getFieldId() + diceRoll) % 40);
            return spielstein.getState() == SpielsteinState.STATE_PLAYING && nextFeld.getIsOccupied() && nextFeld.getOccupier().getColor() != team.getColor();
        });
        return movableSpielsteine.size() != oldSize;
    }

    public Spielstein movePieceOutOfSpawn(Team team) {
        System.out.println("Spielstein aus Spawn bewegen");

        Spielstein currentSpielstein = team.getSpielsteinFromHome();
        currentSpielstein.setState(SpielsteinState.STATE_PLAYING);

        Feld spawn = spielfeld.getFeld(team.getStartField());

        if(spawn.getIsOccupied()){
            System.out.println("Spawn ist besetzt von " + spawn.getOccupier().getColor());
            if(spawn.getOccupier().getColor() == team.getColor()){
                moveSpielstein(spawn.getOccupier(), 6, team);
            } else {
                kickSpielstein(spawn.getOccupier());
                team.pieceFromHomeToField(currentSpielstein);
            }
        }else {
            System.out.println("Spawn ist nicht besetzt");
            team.pieceFromHomeToField(currentSpielstein);
        }

        currentSpielstein.setFieldId(team.getStartField());



        spielfeld.getFeld(team.getStartField()).setOccupier(currentSpielstein);
        return currentSpielstein;
    }

    private void kickSpielstein(Spielstein occupier) {
        System.out.println("Spielstein von " + occupier.getColor() + " wird gekickt");
        occupier.getTeam().pieceFromFieldToHome(occupier);
    }

    private void tryToGetOutOfSpawn(Team team) {
        System.out.println("Versuche aus dem Spawn zu kommen");
        for (int j = 0; j < 3; j++) {
            if (rollDice() == 6) {
                System.out.println("6 gewürfelt, spieler kommt aus dem Spawn");
                moveSpielstein(movePieceOutOfSpawn(team), rollDice(), team);
                break;
            }
        }

    }

    int rollDice() {
        int rand = random.nextInt(6) + 1;
        System.out.println("Würfel zeigt " + rand);
        return rand;
    }

    public boolean checkIfGameIsFinished()
    {
        for(Team team : teams)
        {
            if(team.getIsFinished())
            {
                return true;
            }
        }
        return false;
    }

    private void moveSpielstein(Spielstein spielstein, int diceRoll, Team team) {


        if(spielstein.getState() == SpielsteinState.STATE_HOME && diceRoll == 6){
            System.out.println("Ausgewählter Spielstein ist im Home");
            movePieceOutOfSpawn(team);
            return;
        }


        System.out.println("Bewege Spielstein von " + spielstein.getFieldId());
        System.out.println("Bewege Spielstein um " + diceRoll + " Felder");
        int nextSpielFeld = (spielstein.getFieldId() + diceRoll) % 40;
        int currentSpielFeld = spielstein.getFieldId();

        //If the next field is occupied by an enemy, kick the enemy
        if (spielfeld.getFeld(nextSpielFeld).getIsOccupied()
                && spielfeld.getFeld(nextSpielFeld).getOccupier().getColor() != team.getColor()
                && spielstein.getWalkedFields()+diceRoll <= 39) {
            kickSpielstein(spielfeld.getFeld(nextSpielFeld).getOccupier());
        }

        if(spielstein.getWalkedFields()+diceRoll >= 40 && spielstein.getWalkedFields()+diceRoll <= 44) {
            moveSpielsteinToGoal(team, diceRoll, spielstein);
            return;
        }

        if(spielstein.getState() == SpielsteinState.STATE_HOME){
            moveSpielsteinInGoalAround(team, diceRoll, spielstein);
        }

        spielstein.setFieldId(nextSpielFeld);
        spielfeld.getFeld(currentSpielFeld).setOccupier(null);
        spielstein.setFieldId(nextSpielFeld);
        spielfeld.getFeld(nextSpielFeld).setOccupier(spielstein);
        System.out.println("Spielstein ist auf Feld " + spielstein.getFieldId());
        spielstein.addWalkedFields(diceRoll);

    }

    private void moveSpielsteinInGoalAround(Team team, int diceRoll, Spielstein spielstein) {
        System.out.println("Spielstein ist im Ziel");
        int currentField = spielstein.getFieldId();
        int goalField = currentField + diceRoll;

        System.out.println("Current Field: " + currentField + " Goal Field: " + goalField);
        team.moveSpielsteinAroundFinish(spielstein, currentField, goalField);
        team.checkIfAllPiecesAreInFinish();
    }

    private void moveSpielsteinToGoal(Team team, int diceRoll, Spielstein spielstein) {
        System.out.println("Spielstein ist auf dem Weg ins Ziel");
        System.out.println("Spielstein ist auf Feld " + spielstein.getFieldId());
        System.out.println("Spielstein ist " + spielstein.getWalkedFields() + " Felder gelaufen");
        int currentField = spielstein.getFieldId();
        int goalField = spielstein.getWalkedFields() + diceRoll - 40;

        System.out.println("Current Field: " + currentField + " Goal Field: " + goalField);

        spielfeld.getFeld(currentField).setOccupier(null);
        team.moveSpielsteinToFinish(spielstein, goalField);
        team.checkIfAllPiecesAreInFinish();
    }
}
