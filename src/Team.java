import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Team {
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

    public Team(Color color, int startField, Spielfeld spielfeld) {
        this.startField = startField;
        this.finishField = (startField + 39) %40;
        this.color = color;
        spielsteine = new ArrayList<>();
        for(int i = 0; i<4;i++) {
            spielsteine.add(new Spielstein(this, i));
        }
        this.spielfeld = spielfeld;
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

    public boolean checkIfAllPiecesAreInFinish() {
        for(Spielstein spielstein : spielsteine) {
            if(spielstein.getState() != SpielsteinState.STATE_FINISH) {
                return false;
            }
        }
        return true;
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

    public List<Spielstein> getSpielsteine() {
        return spielsteine;
    }

    public void pieceFromHomeToField(Spielstein spielstein) {
        System.out.println("Bewege Spielstein von Home aufs Feld");
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
}
