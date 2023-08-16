import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Spiel implements Serializable
{
    private boolean gameIsRunning = true;
    private Random random = new Random();
    private List<Team> teams = new ArrayList<Team>();

    private Team nextToPlay = null;

    private static Spielfeld spielfeld;
    private boolean firstRun = false;

    public Spiel()
    {
        spielfeld = new Spielfeld();
        setupPlayers(spielfeld);
    }

    void startGame()
    {

        if(this.nextToPlay != null)
        {
            firstRun = true;
            System.out.println("Das Spiel wird bei Spieler: " + this.nextToPlay.getColor() + " fortgesetzt.");
        }
        else
        {
            System.out.println("Ein neues Spiel wurde gestartet.");
        }


        while (gameIsRunning)
        {
            run();
        }

        System.out.println("Spiel beendet");
    }

    void setupPlayers(Spielfeld spielfeld)
    {
        teams.add(new Team(Color.RED, 24, spielfeld));
        teams.add(new Team(Color.BLUE, 3, spielfeld));
        teams.add(new Team(Color.GREEN, 57, spielfeld));
        teams.add(new Team(Color.YELLOW, 78, spielfeld));
    }

    void run()
    {

        for (int i = 0; i < teams.size(); i++)
        {
            if(firstRun)
            {
                i = teams.indexOf(this.nextToPlay);
            }

            Team team = teams.get(i);

            System.out.println("Team " + team.getColor() + " ist am Zug");
            play(team);
            System.out.println("Team " + team.getColor() + " ist fertig");

            if(team.getIsFinished())
            {
                gameIsRunning = false;
                break;
            }

            if (i == teams.size() - 1 && this.firstRun)
            {
                this.firstRun = false;
            }
        }
    }

    private void play(Team team)
    {
        this.nextToPlay = getNextToPlay(team);

        if (team.checkIfAllPiecesAreInStart())
        {
            tryToGetOutOfSpawn(team);
            return;
        }

        if (!team.getIsFinished())
        {
            int diceRoll = rollDice();
            selectPiece(team, diceRoll);

            if (diceRoll == 6)
            {
                play(team);
            }
        }
    }

    private Team getNextToPlay(Team team)
    {
        return teams.get(((teams.indexOf(team) + 1) % teams.size()));
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

    private void selectPieceUsingGUI(Team team, int diceRoll) {
        List<Spielstein> movableSpielsteine = team.getMovableSpielsteine(diceRoll);

        removePiecesThatWouldOverRun(movableSpielsteine, team, diceRoll);
        removePiecesThatWouldLandOnOwnPiece(team, movableSpielsteine, diceRoll);

        if (movableSpielsteine.isEmpty()) {
            System.out.println("Keine Spielfiguren können bewegt werden");
            return;
        }

        Spielstein selectedSpielstein = null;


        if (selectedSpielstein != null) {
            moveSpielstein(selectedSpielstein, diceRoll, team);
        } else {
            System.out.println("Keine Spielfigur ausgewählt.");
        }
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
        printGameBoard();
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

        System.out.println("NEXT: " + this.getNextToPlay(team).getColor());
        askForSave();
    }


    public void saveGame()
    {
        String userHome = System.getProperty("user.home");

        String persistentDirPath;
        if (System.getProperty("os.name").toLowerCase().contains("win"))
        {
            String appData = System.getenv("APPDATA");
            persistentDirPath = appData + File.separator + "menschaergerdichnicht";
        }
        else
        {
            persistentDirPath = userHome + File.separator + ".menschaergerdichnicht";
        }

        File persistentDir = new File(persistentDirPath);

        if (!persistentDir.exists())
        {
            if (persistentDir.mkdirs())
            {
                System.out.println("Directory created: " + persistentDirPath);
            }
            else
            {
                System.err.println("Failed to create directory: " + persistentDirPath);
            }
        }

        String fileName = "save_game";
        String filePath = persistentDirPath + File.separator + fileName;

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            outputStream.writeObject(this);
            System.out.println("Game saved at: " + filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void askForSave()
    {
        Character input = null;
        System.out.println("Do you want to save a game? (y/n)");

        try
        {
            input = (char) System.in.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        if(input == 'y')
        {
            saveGame();
        }
    }

    public void printGameBoard() {
        System.out.println("Current Game Board:");

        Feld[] fieldArray = spielfeld.getSpielfeldArray();

        for (int i = 0; i < fieldArray.length; i++) {
            Feld field = fieldArray[i];

            System.out.print(String.format("%2d: ", i));

            if (field.isOccupied()) {
                Spielstein occupier = field.getOccupier();
                System.out.print(occupier.getTeam().getColor());
            } else {
                System.out.print("   ");
            }

            System.out.print(" | ");

            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
    }
}
