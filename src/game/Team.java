package game;

import server.ClientHandler;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable, Cloneable {
    //Only Startfield, the fields can be calaculated

    private final Spielstein[] homeFields = new Spielstein[4];
    private final Feld[] finishFields = new Feld[4];
    private int startField;
    private int finishField;
    private List<Spielstein> spielsteine;
    private Color color;
    private boolean isBot;
    private String name;
    private boolean isFinished;
    private final Spielfeld spielfeld;
    private final transient ClientHandler client;

    public Team(Color color, int startField, Spielfeld spielfeld, boolean isBot, ClientHandler clientHandler) {
        this.startField = startField;
        this.finishField = (startField + 39) %40;
        this.color = color;
        spielsteine = new ArrayList<>();
        for(int i = 0; i<4;i++) {
            spielsteine.add(new Spielstein(this));
            finishFields[i] = new Feld();
        }
        this.spielfeld = spielfeld;
        this.client = clientHandler;
        this.isBot = isBot;
    }

    public Color getColor() {
        return color;
    }

    public boolean getIsBot() {
        return isBot;
    }


    public boolean checkIfAllPiecesAreInStart() {
        for(Spielstein spielstein : spielsteine) {
            if(spielstein.getState() != SpielsteinState.STATE_HOME) {
                return false;
            }
        }
        return true;
    }

    public void checkIfAllPiecesAreInFinish() {
        for(Spielstein spielstein : spielsteine) {
            if(spielstein.getState() != SpielsteinState.STATE_FINISH) {
                return;
            }
        }
        System.out.println("game.Team " + name + " Farbe: "+ color + " hat gewonnen!");
        isFinished = true;
    }

    public boolean checkIfPieceIsCorrectInFinish() {
        int amountOfPiecesInFinish = 0;
        int indezes = 0;

        for(int i = 0; i<4;i++){
            if(finishFields[i].getIsOccupied()) {
                amountOfPiecesInFinish++;
                indezes = indezes + i + 1;
            }
        }

        if(amountOfPiecesInFinish == 0 ||
                indezes == 4 ||
                indezes == 7 ||
                indezes == 9 ||
                indezes == 10) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * Gets all the pieces which the player could use to move.
     * @param diceRoll the rolled number
     * @return a List of pieces that the player could use in his turn.
     */
    List<Spielstein> getMovableSpielsteine(int diceRoll)
    {
        List<Spielstein> movableSpielsteine = new ArrayList<>();

        for (Spielstein spielstein : spielsteine) {
            if (spielstein.getState() == SpielsteinState.STATE_PLAYING) {
                movableSpielsteine.add(spielstein);
            }
            if(diceRoll == 6 && spielstein.getState() == SpielsteinState.STATE_HOME) {
                movableSpielsteine.add(spielstein);
            }
        }
        return movableSpielsteine;
    }

    public List<Spielstein> getSpielsteine() {
        return spielsteine;
    }

    public void pieceFromHomeToField(Spielstein spielstein) {
        System.out.println("Bewege game.Spielstein von Home aufs game.Feld");
        spielstein.setFieldId(startField);
        spielstein.setState(SpielsteinState.STATE_PLAYING);
        spielfeld.getFeld(startField).setOccupier(spielstein);
    }

    /**
     * Moves a piece back to one of the teams home fields.
     * @param spielstein the piece that will be moved back to the home fields of the team.
     */
    public void pieceFromFieldToHome(Spielstein spielstein) {
        spielfeld.getFeld(spielstein.getFieldId()).setOccupier(null);
        spielstein.setFieldId(-1);
        addToHome(spielstein);
        spielstein.setWalkedFields(0);
        spielstein.setState(SpielsteinState.STATE_HOME);
    }

    /**
     * Adds a piece to one of the empty home fields.
     * @param spielstein the piece to be moved.
     */
    private void addToHome(Spielstein spielstein) {
        for(int i = 0; i<4;i++) {
            if(homeFields[i] == null) {
                homeFields[i] = spielstein;
                break;
            }
        }
    }

    /**
     * Gets the starting field index of the team.
     * @return the index of the teams start field.
     */
    public int getStartField() {
        return startField;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public Spielstein getSpielsteinFromHome() {
        for(Spielstein spielstein : spielsteine) {
            if(spielstein.getState() == SpielsteinState.STATE_HOME) {
                return spielstein;
            }
        }
        return null;
    }

    public void printFinishFields() {
        for(int i = 0; i<4;i++) {
            System.out.println("FinishField " + i + ": " + finishFields[i].getIsOccupied());
        }
    }

    public int getSpielFeldIntOfSpielsteinInFinish(Spielstein spielstein) {
        for(int i = 0; i<4;i++) {
            if(finishFields[i].getOccupier() == spielstein) {
                return i;
            }
        }
        return -1;
    }
    public boolean getIsOccupiedInFinish(int index) {
        System.out.println("Check if Occupied in finish: " + index);
        return finishFields[index].getIsOccupied();
    }

    public void moveSpielsteinToFinish(Spielstein spielstein, int goalField) {
        System.out.println("Bewege game.Spielstein ins Ziel");
        System.out.println("GoalField: " + goalField);
        spielstein.setFieldId(goalField);
        spielstein.setState(SpielsteinState.STATE_FINISH);
        printFinishFields();
        System.out.println("game.Spielstein: " + spielstein.getFieldId() + " Walked: " + spielstein.getWalkedFields());
        finishFields[goalField].setOccupier(spielstein);
        printFinishFields();
    }

    public void moveSpielsteinAroundFinish(Spielstein spielstein, int currentField, int goalField) {
        System.out.println("Bewege game.Spielstein ums Ziel");
        spielstein.setFieldId(goalField);
        finishFields[currentField].setOccupier(null);
        finishFields[goalField].setOccupier(spielstein);
        printFinishFields();
    }

    /**
     * Gets the teams ClientHandler.
     * @return the ClientHandler of the Team.
     */
    public ClientHandler getClient() {
        return this.client;
    }

    /**
     * Sets the isBot member variable to either {@code true} or {@code false}.
     * @param isBot the boolean that will be set.
     */
    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

    /**
     * Sets the spielsteine List.
     * @param spielsteine the List that will be set.
     */
    public void setSpielsteine(List<Spielstein> spielsteine) {
        this.spielsteine = spielsteine;
    }

    /**
     * Clones the Team object.
     * @return the cloned object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Team team = (Team) super.clone();
        team.startField = startField;
        team.finishField = finishField;
        team.color = color;
        team.isBot = isBot;
        team.name = name;
        team.isFinished = isFinished;
        for(int i = 0; i<4;i++) {
            if(homeFields[i] != null) {
                team.homeFields[i] = (Spielstein) homeFields[i].clone();
            }
            if(finishFields[i] != null) {
                team.finishFields[i] = (Feld) finishFields[i].clone();
            }
        }
        List<Spielstein> copySpielsteine = new ArrayList<>();
        for(Spielstein spielstein : spielsteine) {
            copySpielsteine.add((Spielstein) spielstein.clone());
        }
        team.setSpielsteine(copySpielsteine);
        return team;
    }

    /**
     * provided a way to print out the Team information.
     * @return the string with the teams' information.
     */
    @Override
    public String toString() {
        return "game.Team{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", startField=" + startField +
                ", finishField=" + finishField +
                ", isBot=" + isBot +
                ", isFinished=" + isFinished +
                ", spielsteine=" + spielsteine +
                '}';
    }
}
