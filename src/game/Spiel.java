package game;

import server.ClientHandler;
import server.ClientsBroadcast;

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

    private Team currentlyPlaying = null;
    private Integer lastDiceRoll = null;
    private Spielfeld spielfeld;
    private boolean firstRun = false;

    private ClientsBroadcast clientsBroadcast;

    private boolean doBroadcast;

    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    @Deprecated
    public Spiel()
    {
        spielfeld = new Spielfeld();
        setupPlayers(spielfeld);
    }

    /**
     * Konstruktor der Klasse "Spiel".
     * Erstellt das Spielfeld initialisiert die Spieler.
     * @param isLocal
     * @param numPlayers Die Anzahl der Spieler die am Spiel teilnehmen.
     * @param clients Die Liste der verbundenen Spieler.
     */
    public Spiel(Boolean isLocal, int numPlayers, List<ClientHandler> clients) {
        spielfeld = new Spielfeld();
        setupClients(clients, numPlayers);
        this.clientsBroadcast = new ClientsBroadcast(clients);
    }


    /**
     * Startet ein neues Spiel oder setzt ein angefangenes Spiel fort.
     */
    public void startGame(boolean doBroadcast)
    {
        this.doBroadcast = doBroadcast;

        if(currentlyPlaying != null)
        {
            firstRun = true;
            System.out.println("Das game.Spiel wird bei Spieler: " + this.currentlyPlaying.getColor() + " fortgesetzt.");
        }
        else
        {
            System.out.println("Ein neues game.Spiel wurde gestartet.");
        }

        doBroadcast();

        while (gameIsRunning)
        {
            run();
            doBroadcast();
        }

        System.out.println("game.Spiel beendet");
    }

    @Deprecated
    void setupPlayers(Spielfeld spielfeld)
    {

        teams.add(new Team(Color.RED, 0, spielfeld, false, null));
        teams.add(new Team(Color.BLUE, 10, spielfeld, false, null));
        teams.add(new Team(Color.GREEN, 20, spielfeld, false, null));
        teams.add(new Team(Color.YELLOW, 30, spielfeld, false, null));

    }

    /**
     * Fügt Teams für verbundene Spieler hinzu und füllt fehlende Spieler mit Bots auf.
     * @param clients Die Liste der verbundenen Spieler.
     * @param numPlayers Die Anzahl der Spieler die am Spiel teilnehmen.
     */
    void setupClients(List<ClientHandler> clients, int numPlayers) {
        for(int i = 0; i < numPlayers; i++) {
            teams.add(new Team(colors[i], i*10, spielfeld, false, clients.get(i)));
        }
        for(int k = numPlayers; k < 4; k++) {
            teams.add(new Team(colors[k], k*10, spielfeld, true, null));
        }
    }

    void run()
    {

        for (int i = 0; i < teams.size(); i++)
        {
            if(firstRun)
            {
                i = teams.indexOf(this.currentlyPlaying);
                firstRun = false;
            }

            Team team = teams.get(i);

            System.out.println("game.Team " + team.getColor() + " ist am Zug");
            play(team);
            System.out.println("\ngame.Team " + team.getColor() + " ist fertig");

            if(team.getIsFinished())
            {
                gameIsRunning = false;
                break;
            }

            currentlyPlaying = getNextToPlay(team);

            /*
            if (i == teams.size() - 1 && this.firstRun)
            {
                this.firstRun = false;
            }
             */
        }
    }

    private void play(Team team)
    {
        currentlyPlaying = team;
        if (team.checkIfAllPiecesAreInStart())
        {
            tryToGetOutOfSpawn(team);
            return;
        }

        if (!team.getIsFinished())
        {
            int diceRoll;
            if(lastDiceRoll == null)
            {
                diceRoll = rollDice();
                lastDiceRoll = diceRoll;
            }
            else
            {
                diceRoll = lastDiceRoll;
            }

            selectPiece(team, diceRoll);

            lastDiceRoll = null;

            if (diceRoll == 6)
            {
                play(team);
            }
        }

        lastDiceRoll = null;
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
        //TODO: game.Spielstein sollte nachdem es im Ziel ist die SpielFeldID des momentanen Feldes im Ziel bekommen
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
        System.out.println("game.Spielstein aus Spawn bewegen");

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

        doBroadcast();

        return currentSpielstein;
    }

    /**
     * Wirft einen Spielstein und setzt diesen wieder zurück in das Startfeld des jeweiligen Spielers.
     * @param occupier Der zu werfende Spielstein.
     */
    private void kickSpielstein(Spielstein occupier) {
        System.out.println("game.Spielstein von " + occupier.getColor() + " wird gekickt");
        occupier.getTeam().pieceFromFieldToHome(occupier);
        doBroadcast();
    }

    /**
     * Lässt das betroffene Team dreimal würfel. Sollte dabei eine 6 gewürfelt werden
     * wird ein Spielstein des Teams aus dem Start heraus gerückt.
     * @param team Das Team welches an der Reihe ist.
     */
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

    /**
     * Würfelt eine zufällige Zahl
     * @return Gibt die gewürfelte Zahl zurück.
     */
    int rollDice() {
        int rand = random.nextInt(6) + 1;
        System.out.println("Würfel zeigt " + rand);
        return rand;
    }

    /**
     * Iteriert durch alle Teams und überprüft, ob das Team fertig ist.
     * @return Gibt {@code true} zurück, wenn das Spiel beendet ist.
     * Ansonsten wird entsprechend {@code false} zurückgegeben.
     */
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

    /**
     * Rückt einen Spielstein um eine gewisse Anzahl an Feldern.
     * @param spielstein Der zu rückende Spielstein
     * @param diceRoll Die gewürfelte Zahl, um welche der Spielstein gerückt wird.
     * @param team Das Team, welches am Zug ist.
     */
    private void moveSpielstein(Spielstein spielstein, int diceRoll, Team team) {


        if(spielstein.getState() == SpielsteinState.STATE_HOME && diceRoll == 6){
            System.out.println("Ausgewählter game.Spielstein ist im Home");
            movePieceOutOfSpawn(team);
            return;
        }


        System.out.println("Bewege game.Spielstein von " + spielstein.getFieldId());
        System.out.println("Bewege game.Spielstein um " + diceRoll + " Felder");
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


        moveSpielsteinStepByStep(nextSpielFeld, spielstein, currentSpielFeld, diceRoll);

    }

    private void moveSpielsteinStepByStep(int nextSpielFeld, Spielstein spielstein, int currentSpielFeld, int diceRoll) {

        while(spielstein.getFieldId() != nextSpielFeld){

            spielstein.setFieldId((spielstein.getFieldId() + 1) % 40);
            spielfeld.getFeld(currentSpielFeld).setOccupier(null);
            spielfeld.getFeld(spielstein.getFieldId()).setOccupier(spielstein);
            currentSpielFeld = spielstein.getFieldId();

            doBroadcast();
        }

        System.out.println("game.Spielstein ist auf game.Feld " + spielstein.getFieldId());
        spielstein.addWalkedFields(diceRoll);
        doBroadcast();
    }

    private void moveSpielsteinInGoalAround(Team team, int diceRoll, Spielstein spielstein) {
        System.out.println("game.Spielstein ist im Ziel");
        int currentField = spielstein.getFieldId();
        int goalField = currentField + diceRoll;

        System.out.println("Current Field: " + currentField + " Goal Field: " + goalField);
        team.moveSpielsteinAroundFinish(spielstein, currentField, goalField);
        team.checkIfAllPiecesAreInFinish();
        doBroadcast();
    }

    /**
     * Bewegt einen Spielstein von den normalen Feldern in eines der Zielfelder.
     * @param team Das Team, welches am Zug ist.
     * @param diceRoll Die gewürfelte Zahl, um welche der Spielstein gerückt wird.
     * @param spielstein Der zu rückende Spielstein
     */
    private void moveSpielsteinToGoal(Team team, int diceRoll, Spielstein spielstein) {
        System.out.println("game.Spielstein ist auf dem Weg ins Ziel");
        System.out.println("game.Spielstein ist auf game.Feld " + spielstein.getFieldId());
        System.out.println("game.Spielstein ist " + spielstein.getWalkedFields() + " Felder gelaufen");
        int currentField = spielstein.getFieldId();
        int goalField = spielstein.getWalkedFields() + diceRoll - 40;

        System.out.println("Current Field: " + currentField + " Goal Field: " + goalField);

        spielfeld.getFeld(currentField).setOccupier(null);
        team.moveSpielsteinToFinish(spielstein, goalField);
        team.checkIfAllPiecesAreInFinish();

        System.out.println("NEXT: " + this.getNextToPlay(team).getColor());
        doBroadcast();
        //askForSave();

    }

    /**
     * Speichert den Spielstand in einer Datei ab.
     */
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

        int i = 0;
        while(new File(filePath).exists())
        {
            i++;
            String newFileName = fileName + i;
            filePath = persistentDirPath + File.separator + newFileName;

        }

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

    void doBroadcast() {
        if(doBroadcast) {
            clientsBroadcast.broadcast(this);
        }
    }
}
