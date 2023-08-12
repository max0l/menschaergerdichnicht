package game;

import server.ClientHandler;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    //Only Startfield, the fields can be calaculated

    private Spielstein[] homeFields = new Spielstein[4];
    private Feld[] finishFields = new Feld[4];
    private int startField;
    private int finishField;

    private List<Spielstein> spielsteine;

    private Color color;

    private boolean isBot;
    private String name;
    private boolean isFinished;

    private Spielfeld spielfeld;
    
    private ClientHandler client;

    public Team(Color color, int startField, Spielfeld spielfeld, boolean isBot, ClientHandler clientHandler) {
        this.startField = startField;
        this.finishField = (startField + 39) %40;
        this.color = color;
        spielsteine = new ArrayList<>();
        for(int i = 0; i<4;i++) {
            spielsteine.add(new Spielstein(this, i));
            finishFields[i] = new Feld(i);
        }
        this.spielfeld = spielfeld;
        this.client = clientHandler;
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

    //
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

    public void pieceFromFieldToHome(Spielstein spielstein) {
        spielfeld.getFeld(spielstein.getFieldId()).setOccupier(null);
        spielstein.setFieldId(-1);
        addToHome(spielstein);
        spielstein.setState(SpielsteinState.STATE_HOME);
    }


    private void addToHome(Spielstein spielstein) {
        for(int i = 0; i<4;i++) {
            if(homeFields[i] == null) {
                homeFields[i] = spielstein;
                break;
            }
        }
    }


    public int getStartField() {
        return startField;
    }

    public int getEndField() {
        return finishField;
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

    public ClientHandler getClient() {
        return client;
    }
}
