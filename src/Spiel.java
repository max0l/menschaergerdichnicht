import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spiel {
    private boolean gameIsRunning = true;
    private Random random = new Random();

    private Teams teams;
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

    void setupPlayers(Spielfeld spielfeld) {
        List<Team> generatedTeams = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            generatedTeams.add(new Team(Color.RED, 0, spielfeld));
            generatedTeams.add(new Team(Color.BLUE, 10, spielfeld));
            generatedTeams.add(new Team(Color.GREEN, 20, spielfeld));
            generatedTeams.add(new Team(Color.YELLOW, 30, spielfeld));
        }
        teams = new Teams(generatedTeams);
    }

    void run() {
        for (Team team : teams.getTeams()) {
            System.out.println("Team " + team.getColor() + " ist am Zug");
            makeAmove(team);
            System.out.println("Team " + team.getColor() + " ist fertig" + "\n\n");

            if(team.getIsFinished())
            {
                System.out.println("Spiel beendet");
                gameIsRunning = false;
            }
        }
    }

    private void makeAmove(Team team) {
        switch (getTeamStates(team)) {
            case allHome:
                System.out.println("Alle Spielsteine sind im Startfeld");
                tryToGetOutOfSpawn(team);
                break;
            case allFinish:
                break;
            case playing:
                play(team);
                break;
        }
    }

    private void play(Team team) {
        int diceRoll = rollDice();
        if (diceRoll == 6) {
            selectPiece(team, diceRoll);
            play(team);
        } else {
            selectPiece(team, diceRoll);
        }
    }

    private void selectPiece(Team team, int diceRoll) {
        List<Spielstein> movableSpielsteine = new ArrayList<>();
        //TODO: Selection muss angepasst werden. Es soll nur ein stein ausgewählt werden
        //Spieler (und Bot) müssen jetzt auswhlen welcher Stein bewegt werden soll, Bei einer 6 kann auch ein Spielstein
        //aus dem Home ausgewählt werden
        if(diceRoll == 6) {
            for (Spielstein spielstein : team.getSpielsteine()) {
                if (spielstein.getState() == SpielsteinState.STATE_HOME || spielstein.getState() == SpielsteinState.STATE_PLAYING) {
                    movableSpielsteine.add(spielstein);
                }
            }
        } else {
            for (Spielstein spielstein : team.getSpielsteine()) {
                if (spielstein.getState() == SpielsteinState.STATE_PLAYING) {
                    movableSpielsteine.add(spielstein);
                }
            }
        }

        if (movableSpielsteine.size() == 1) {
            moveSpielstein(movableSpielsteine.get(0), diceRoll, team);
        } else if (checkIfPieceCanKickOtherPiece(team, diceRoll, movableSpielsteine)) {

        } else {
            //TODO: Selektion must be implemented for human
            moveSpielstein(movableSpielsteine.get(random.nextInt(movableSpielsteine.size())), diceRoll, team);
        }
    }

    private boolean checkIfPieceCanKickOtherPiece(Team team, int diceRoll, List<Spielstein> movableSpielsteine) {
        //Selection must be implemented for human
        for (Spielstein spielstein : movableSpielsteine) {
            Feld nextFeld = spielfeld.getFeld((spielstein.getFieldId() + diceRoll) % 40);
            if(nextFeld.getIsOccupied()) {
                if(nextFeld.getOccupier().getColor() != team.getColor()) {
                    kickSpielstein(nextFeld.getOccupier());
                    moveSpielstein(spielstein, diceRoll, team);
                    return true;
                }
                else{
                    movableSpielsteine.remove(spielstein);
                }
            }
        }
        return false;
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

    //Vielleicht redundant
    private teamStates getTeamStates(Team team) {
        if (team.checkIfAllPiecesAreInStart()) {
            return teamStates.allHome;
        } else if (team.checkIfAllPiecesAreInFinish()) {
            return teamStates.allFinish;
        } else {
            return teamStates.playing;
        }
    }

    int rollDice() {
        int rand = random.nextInt(6) + 1;
        System.out.println("Würfel zeigt " + rand);
        return rand;
    }

    private void moveSpielstein(Spielstein spielstein, int diceRoll, Team team) {


        if(spielstein.getState() == SpielsteinState.STATE_HOME){
            System.out.println("Ausgewählter Spielstein ist im Home");
            movePieceOutOfSpawn(team);
        } else {
            System.out.println("Bewege Spielstein von " + spielstein.getFieldId());
            System.out.println("Bewege Spielstein um " + diceRoll + " Felder");
            int nextSpielFeld = (spielstein.getFieldId() + diceRoll) % 40;
            int currentSpielFeld = spielstein.getFieldId();

            spielstein.setFieldId(nextSpielFeld);
            spielfeld.getFeld(currentSpielFeld).setOccupier(null);
            spielstein.setFieldId(nextSpielFeld);
            spielfeld.getFeld(nextSpielFeld).setOccupier(spielstein);
            System.out.println("Spielstein ist auf Feld " + spielstein.getFieldId());

            //checks if the Piece is in the range of the endfield
        /*
        if (spielstein.getFieldId() < team.getEndField()) {
            spielstein.setFieldId();
        } else if (spielstein.getFieldId() >= team.getEndField()) {
            //TODO: Needs to be implemented: Logic that handles entering the goal
        }

        if (spielsteinCanKick(spielstein, diceRoll, team)) {
            kickSpielstein();
        } else {
            moveSpielstein(spielstein, diceRoll, team);
        }

         */
        }
    }
    /*
    private boolean spielsteinCanKick(Spielstein spielstein, int diceRoll, Team team) {
        int newFieldId = (spielstein.getFieldId() + diceRoll) % 40;

        for (Spielstein otherSpielstein : team.getSpielsteine()) {
            if (otherSpielstein.getFieldId() == newFieldId) {
                return true;
            }
        }
        return false;
    }

     */
}
