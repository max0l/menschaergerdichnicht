package game;

import server.ClientHandler;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable, Cloneable {
    private final Piece[] homeFields = new Piece[4];
    private final Field[] finishFields = new Field[4];
    private int startField;
    private int finishField;
    private List<Piece> spielsteine;
    private Color color;
    private boolean isBot;
    private String name;
    private boolean isFinished;
    private final PlayingField playingField;
    private final transient ClientHandler client;

    /**
     * Constructor of the Team class. Initializes member variables.
     *
     * @param color         the color the team will get.
     * @param startField    the index of the games starting field.
     * @param playingField  the play field.
     * @param isBot         decided whether this team is played by a bot.
     * @param clientHandler the ClientHandler for Team.
     */
    public Team(Color color, int startField, PlayingField playingField, boolean isBot, ClientHandler clientHandler) {
        this.startField = startField;
        this.finishField = (startField + 39) % 40;
        this.color = color;
        spielsteine = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            spielsteine.add(new Piece(this));
            finishFields[i] = new Field();
        }
        this.playingField = playingField;
        this.client = clientHandler;
        this.isBot = isBot;
    }

    /**
     * Gets the Teams Color
     *
     * @return the Color of the Team.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieves whether the team is played by a bot.
     *
     * @return {@code true} if the team is played by a bot. Otherwise {@code false}.
     */
    public boolean getIsBot() {
        return isBot;
    }


    /**
     * Checks if all pieces of the team are placed in the home fields.
     *
     * @return {@code true} if all pieces are at home. Otherwise {@code false}.
     */
    public boolean checkIfAllPiecesAreInStart() {
        for (Piece piece : spielsteine) {
            if (piece.getState() != PieceState.STATE_HOME) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all pieces of the team are placed in the finish fields and
     * sets isFinished to true if this case is met.
     */
    public void checkIfAllPiecesAreInFinish() {
        for (Piece piece : spielsteine) {
            if (piece.getState() != PieceState.STATE_FINISH) {
                return;
            }
        }
        System.out.println("game.Team " + name + " Farbe: " + color + " hat gewonnen!");
        isFinished = true;
    }

    /**
     * Gets all the pieces which the player could use to move.
     *
     * @param diceRoll the rolled number
     * @return a List of pieces that the player could use in his turn.
     */
    List<Piece> getMovableSpielsteine(int diceRoll) {
        List<Piece> movableSpielsteine = new ArrayList<>();

        for (Piece piece : spielsteine) {
            if (piece.getState() == PieceState.STATE_PLAYING) {
                movableSpielsteine.add(piece);
            }
            if (diceRoll == 6 && piece.getState() == PieceState.STATE_HOME) {
                movableSpielsteine.add(piece);
            }
        }
        return movableSpielsteine;
    }

    /**
     * Retrieves the team pieces.
     *
     * @return the teams list of pieces.
     */
    public List<Piece> getSpielsteine() {
        return spielsteine;
    }

    /**
     * Moves a piece from the teams home fields to the teams start field.
     *
     * @param piece the piece to be moved.
     */
    public void pieceFromHomeToField(Piece piece) {
        System.out.println("Moving piece from home to field");
        piece.setFieldId(startField);
        piece.setState(PieceState.STATE_PLAYING);
        playingField.getFeld(startField).setOccupier(piece);
    }

    /**
     * Moves a piece back to one of the teams home fields.
     *
     * @param piece the piece that will be moved back to the home fields of the team.
     */
    public void pieceFromFieldToHome(Piece piece) {
        playingField.getFeld(piece.getFieldId()).setOccupier(null);
        piece.setFieldId(-1);
        addToHome(piece);
        piece.setWalkedFields(0);
        piece.setState(PieceState.STATE_HOME);
    }

    /**
     * Adds a piece to one of the empty home fields.
     *
     * @param piece the piece to be moved.
     */
    private void addToHome(Piece piece) {
        for (int i = 0; i < 4; i++) {
            if (homeFields[i] == null) {
                homeFields[i] = piece;
                break;
            }
        }
    }

    /**
     * Gets the starting field index of the team.
     *
     * @return the index of the teams start field.
     */
    public int getStartField() {
        return startField;
    }

    /**
     * Retrieves whether the team is finished.
     *
     * @return {@code true} if the team is finished, {@code false} otherwise.
     */
    public boolean getIsFinished() {
        return isFinished;
    }

    /**
     * Gets a piece from the teams home fields.
     *
     * @return the selected piece.
     */
    public Piece getSpielsteinFromHome() {
        for (Piece piece : spielsteine) {
            if (piece.getState() == PieceState.STATE_HOME) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Prints out the finish fields of the team, and whether they are occupied or not.
     */
    public void printFinishFields() {
        for (int i = 0; i < 4; i++) {
            System.out.println("FinishField " + i + ": " + finishFields[i].getIsOccupied());
        }
    }

    /**
     * Gets the finish field index of a particular piece.
     *
     * @param piece the piece we want the index of.
     * @return {@code -1} if the piece could not be found in the finish fields. Otherwise, the index of the piece.
     */
    public int getSpielFeldIntOfSpielsteinInFinish(Piece piece) {
        for (int i = 0; i < 4; i++) {
            if (finishFields[i].getOccupier() == piece) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if a particular finish field is occupied.
     *
     * @param index the index if the field that will be checked.
     * @return {@code true} if the field is occupied. Otherwise {@code false}.
     */
    public boolean getIsOccupiedInFinish(int index) {
        return finishFields[index].getIsOccupied();
    }

    /**
     * Moves a piece from the normal game fields to one of the teams finish fields.
     *
     * @param piece     the piece to be moved.
     * @param goalField the index of the goal field where the piece will be moved.
     */
    public void moveSpielsteinToFinish(Piece piece, int goalField) {
        System.out.println("Moving piece to goal");

        piece.setFieldId(goalField);
        piece.setState(PieceState.STATE_FINISH);
        printFinishFields();
        finishFields[goalField].setOccupier(piece);
        printFinishFields();
    }

    /**
     * Moves a piece from one finish field to another finish field.
     *
     * @param piece        the piece that will be moved.
     * @param currentField the current field of the piece.
     * @param goalField    the new finish field of the piece.
     */
    public void moveSpielsteinAroundFinish(Piece piece, int currentField, int goalField) {
        System.out.println("Bewege game.Spielstein ums Ziel");
        piece.setFieldId(goalField);
        finishFields[currentField].setOccupier(null);
        finishFields[goalField].setOccupier(piece);
        printFinishFields();
    }

    /**
     * Gets the teams ClientHandler.
     *
     * @return the ClientHandler of the Team.
     */
    public ClientHandler getClient() {
        return this.client;
    }

    /**
     * Sets the isBot member variable to either {@code true} or {@code false}.
     *
     * @param isBot the boolean that will be set.
     */
    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

    /**
     * Sets the spielsteine List.
     *
     * @param spielsteine the List that will be set.
     */
    public void setSpielsteine(List<Piece> spielsteine) {
        this.spielsteine = spielsteine;
    }

    /**
     * Clones the Team object.
     *
     * @return the cloned object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Team clone() throws CloneNotSupportedException {
        Team team = (Team) super.clone();
        team.startField = startField;
        team.finishField = finishField;
        team.color = color;
        team.isBot = isBot;
        team.name = name;
        team.isFinished = isFinished;
        for (int i = 0; i < 4; i++) {
            if (homeFields[i] != null) {
                team.homeFields[i] = homeFields[i].clone();
            }
            if (finishFields[i] != null) {
                team.finishFields[i] = finishFields[i].clone();
            }
        }
        List<Piece> copySpielsteine = new ArrayList<>();
        for (Piece piece : spielsteine) {
            copySpielsteine.add(piece.clone());
        }
        team.setSpielsteine(copySpielsteine);
        return team;
    }

    /**
     * provided a way to print out the Team information.
     *
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