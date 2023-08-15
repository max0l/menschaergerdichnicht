package game;

import server.ClientHandler;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Spiel implements Serializable, Cloneable
{
    public Spielfeld setSpielfeld;
    private List<Team> teams;
    private boolean gameIsRunning = true;
    private Random random = new Random();
    private Team currentlyPlaying = null;
    private Integer lastDiceRoll;
    private Spielfeld spielfeld;
    private boolean firstRun = false;

    private int numPlayers;

    /**
     * Konstruktor der Klasse "Spiel".
     * Erstellt das Spielfeld initialisiert die Spieler.
     *
     * @param isLocal
     * @param numPlayers Die Anzahl der Spieler die am Spiel teilnehmen.
     * @param spielfeld
     */
    public Spiel(Boolean isLocal, int numPlayers, List<Team> teams, Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
        this.teams = teams;
        this.numPlayers = teams.size();
    }

    public Spiel(boolean isLocal, int size, List<Team> teams, Spielfeld spielfeld, Team currentlyPlaying, int lastDiceRoll, boolean gameIsRunning) {
        numPlayers = size;
        this.spielfeld = spielfeld;
        this.teams = teams;
        this.numPlayers = teams.size();
        this.currentlyPlaying = currentlyPlaying;
    }

    public Spiel(boolean isLocal, int numPlayers, List<ClientHandler> clients, Color[] colors) {
        this.numPlayers = numPlayers;
        this.teams = new ArrayList<>();
        this.spielfeld = new Spielfeld();
        this.gameIsRunning = true;

        giveEachClientTheirTeam(spielfeld, clients, colors);

        //Create Bots
        for(int i = numPlayers; i < 4; i++){
            teams.add(new Team(colors[i], i*10, spielfeld, true, null));
        }

        System.out.println("SERVER:\t\tTeam Size of clients and bots: " + teams.size());

    }

    private void giveEachClientTheirTeam(Spielfeld spielfeld, List<ClientHandler> clients, Color[] colors) {
        for(int i = 0; i < clients.size(); i++){
            Team team = new Team(colors[i], i*10, spielfeld, false, clients.get(i));
            teams.add(team);
            clients.get(i).setTeam(team);
        }

        System.out.println("SERVER:\t\tTeam Size of clients: " + teams.size());
    }


    /**
     * Startet ein neues Spiel oder setzt ein angefangenes Spiel fort.
     */
    public void startGame(boolean doBroadcast)
    {

        if(currentlyPlaying != null)
        {
            firstRun = true;
            System.out.println("Das game.Spiel wird bei Spieler: " + this.currentlyPlaying.getColor() + " fortgesetzt.");
        }
        else
        {
            System.out.println("Ein neues game.Spiel wurde gestartet.");
        }

    }


//    public void play(Team team)
//    {
//
//        currentlyPlaying = team;
//        if (team.checkIfAllPiecesAreInStart())
//        {
//            tryToGetOutOfSpawn(team);
//            return;
//        }
//
//        if (!team.getIsFinished())
//        {
//            int diceRoll;
//            if(lastDiceRoll == null)
//            {
//                diceRoll = rollDice();
//                lastDiceRoll = diceRoll;
//            }
//            else
//            {
//                diceRoll = lastDiceRoll;
//            }
//
//            selectPiece(team, diceRoll);
//
//            lastDiceRoll = null;
//
//            if (diceRoll == 6)
//            {
//                play(team);
//            }
//        }
//
//        lastDiceRoll = null;
//    }

    private Team getNextToPlay(Team team)
    {
        return teams.get(((teams.indexOf(team) + 1) % teams.size()));
    }

    public List<Spielstein> selectPiece(Team team, int diceRoll) {
        List<Spielstein> movableSpielsteine = team.getMovableSpielsteine(diceRoll);

        removePiecesThatWouldOverRun(movableSpielsteine, team, diceRoll);
        removePiecesThatWouldLandOnOwnPiece(team, movableSpielsteine, diceRoll);


//        if (movableSpielsteine.size() == 1) {
//            moveSpielstein(movableSpielsteine.get(0), diceRoll, team);
//            return movableSpielsteine;
//        }




        //This else if case checks if there is a piece on the spawn and if there is one, it will be moved from the spawn
        //Because You have to move out of spawn if you rolled a 6
        //It checks if the spawn is occupied by a piece of the same color
        if(diceRoll == 6 && checkIfSpawnIsOccupiedBySameTeam(team)) {
            movableSpielsteine.removeIf(
                    spielstein -> spielstein.getState() == SpielsteinState.STATE_PLAYING
                            && spielstein.getFieldId() != team.getStartField());
            return movableSpielsteine;
        }

        //This else if case checks if there is a piece in home and if there is one, it should be used because the rules
        //say that you have to move first all stones out of spawn before you can use a 6 on another piece
        else if (diceRoll == 6 && (movableSpielsteine.stream().anyMatch(spielstein -> spielstein.getState() == SpielsteinState.STATE_HOME))) {
            movableSpielsteine.removeIf(spielstein -> spielstein.getState() == SpielsteinState.STATE_PLAYING);

        }
        //This else if will check if a piece on the field can kick another one and removed all pieces that can't kick
        else if (checkIfPieceCanKickOtherPiece(team, diceRoll, movableSpielsteine)) {
            System.out.println("Es kann eine andere Spielfigur geschlagen werden");
            System.out.println("movableSpielsteine: " + movableSpielsteine.size());
        }
        if(movableSpielsteine.isEmpty()) {
            System.out.println("Keine Spielfiguren können bewegt werden");
            return null;
        }
        return movableSpielsteine;
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

    //Checks if the spawn is occupied by a piece of the same color
    private boolean checkIfSpawnIsOccupiedBySameTeam(Team team) {
        Feld spawn = spielfeld.getFeld(team.getStartField());
        return spawn.getIsOccupied() && spawn.getOccupier().getColor() == team.getColor();
    }

    private boolean checkIfPieceCanKickOtherPiece(Team team, int diceRoll, List<Spielstein> movableSpielsteine) {
        int oldSize = movableSpielsteine.size();
        movableSpielsteine.removeIf(spielstein -> {
            Feld nextFeld = spielfeld.getFeld((spielstein.getFieldId() + diceRoll) % 40);
            return spielstein.getState() == SpielsteinState.STATE_PLAYING && nextFeld.getIsOccupied() && nextFeld.getOccupier().getColor() == team.getColor();
        });
        return movableSpielsteine.size() != oldSize;
    }

    private Spielstein movePieceOutOfSpawn(Team team) {
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

        return currentSpielstein;
    }

    /**
     * Wirft einen Spielstein und setzt diesen wieder zurück in das Startfeld des jeweiligen Spielers.
     * @param occupier Der zu werfende Spielstein.
     */
    private void kickSpielstein(Spielstein occupier) {
        System.out.println("game.Spielstein von " + occupier.getColor() + " wird gekickt");
        occupier.getTeam().pieceFromFieldToHome(occupier);
    }

    /**
     * Lässt das betroffene Team dreimal würfel. Sollte dabei eine 6 gewürfelt werden
     * wird ein Spielstein des Teams aus dem Start heraus gerückt.
     * @param team Das Team welches an der Reihe ist.
     */
    public void tryToGetOutOfSpawn(Team team) {
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
    public int rollDice() {
        int rand = random.nextInt(6) + 1;
        System.out.println("Würfel zeigt " + rand);
        lastDiceRoll = rand;
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
    public void moveSpielstein(Spielstein spielstein, int diceRoll, Team team) {

        if(spielstein == null){
            System.out.println("Kein Spielstein ausgewählt");
            return;
        }

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

        }

        System.out.println("game.Spielstein ist auf game.Feld " + spielstein.getFieldId());
        spielstein.addWalkedFields(diceRoll);
    }

    private void moveSpielsteinInGoalAround(Team team, int diceRoll, Spielstein spielstein) {
        System.out.println("game.Spielstein ist im Ziel");
        int currentField = spielstein.getFieldId();
        int goalField = currentField + diceRoll;

        System.out.println("Current Field: " + currentField + " Goal Field: " + goalField);
        team.moveSpielsteinAroundFinish(spielstein, currentField, goalField);
        team.checkIfAllPiecesAreInFinish();
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


    public Integer getLastDiceRoll() {
        return lastDiceRoll;
    }

    public void setLastDiceRoll(Integer lastDiceRoll) {
        this.lastDiceRoll = lastDiceRoll;
    }

    public Team getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public Team getTeamByColor(Color teamColor) {
        for(Team team : teams){
            if(team.getColor() == teamColor){
                return team;
            }
        }
        return null;
    }

    public void setCurrentlyPlaying(Team team) {
        this.currentlyPlaying = team;
    }

//    @Serial
//    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
//        currentlyPlaying = (Team) inputStream.readObject();
//        lastDiceRoll = (Integer) inputStream.readObject();
//        spielfeld = (Spielfeld) inputStream.readObject();
//        numPlayers = inputStream.readInt();
//        teams = new ArrayList<>();
//        for(int i = 0; i < numPlayers; i++){
//            Team team = (Team) inputStream.readObject();
//            teams.add(team);
//        }
//        gameIsRunning = inputStream.readBoolean();
//    }
//
//    @Serial
//    private void writeObject(ObjectOutputStream outputStream) throws IOException {
//        outputStream.writeObject(currentlyPlaying);
//        outputStream.writeObject(lastDiceRoll);
//        outputStream.writeObject(spielfeld);
//        outputStream.writeInt(numPlayers);
//        for(int i = 0; i < numPlayers; i++){
//            outputStream.writeObject(teams.get(i));
//        }
//        outputStream.writeBoolean(gameIsRunning);
//    }

    public List<Team> getTeams() {
        return teams;
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Spiel clone = (Spiel) super.clone();
        clone.teams = new ArrayList<>();
        for(Team team : teams){
            clone.teams.add((Team) team.clone());
        }
        clone.spielfeld = (Spielfeld) spielfeld.clone();
        clone.numPlayers = numPlayers;
        clone.gameIsRunning = gameIsRunning;
        clone.currentlyPlaying = (Team) currentlyPlaying.clone();
        clone.lastDiceRoll = lastDiceRoll;
        clone.firstRun = firstRun;



        return clone;
    }

    public boolean isGameIsRunning() {
        return gameIsRunning;
    }

    public void setIsGameRunning(boolean b) {
        gameIsRunning = b;
    }

    public void setSetSpielfeld(Spielfeld setSpielfeld) {
        this.setSpielfeld = setSpielfeld;
    }
}