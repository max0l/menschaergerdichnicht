package game;

import java.awt.*;
import java.io.Serializable;

public class Spielstein implements Serializable, Cloneable {
    private Color color;
    private SpielsteinState state;
    private int fieldId;



    private int walkedFields;

    private Team team;

    public Spielstein(Team team, int steinID) {
        this.team = team;
        color = team.getColor();
        state = SpielsteinState.STATE_HOME;
        fieldId = -1;
    }

    public Color getColor() {
        return color;
    }

    public SpielsteinState getState() {
        return state;
    }

    public void setState(SpielsteinState state) {
        this.state = state;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public void moveToSpawn() {
        fieldId = -1;
        walkedFields = 0;
    }
    public Team getTeam() {
        return team;
    }

    public int getWalkedFields() {
        return walkedFields;
    }

    public void setWalkedFields(int walkedFields) {
        this.walkedFields = walkedFields;
    }

    public void addWalkedFields(int diceRoll) {
        walkedFields += diceRoll;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        Spielstein spielstein = (Spielstein) super.clone();
        spielstein.color = color;
        spielstein.state = state;
        spielstein.fieldId = fieldId;
        spielstein.walkedFields = walkedFields;
        spielstein.team = team;
        return spielstein;
    }
}
