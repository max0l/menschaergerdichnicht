package game;

import java.awt.*;
import java.io.Serializable;

public class Spielstein implements Serializable, Cloneable {
    private Color color;
    private SpielsteinState state;
    private int fieldId;
    private int walkedFields;
    private Team team;

    /**
     * Constructor of the Spielstein class. Initializes member variables.
     * @param team the team that owns the piece
     */
    public Spielstein(Team team) {
        this.team = team;
        color = team.getColor();
        state = SpielsteinState.STATE_HOME;
        fieldId = -1;
    }

    /**
     * Gets the color of the piece (equal to the owning teams color).
     * @return the piece color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the state of the piece.
     * @return the state.
     */
    public SpielsteinState getState() {
        return state;
    }

    /**
     * Sets the state of the piece.
     * @param state the new state.
     */
    public void setState(SpielsteinState state) {
        this.state = state;
    }

    /**
     * Retrieves the fieldId of the piece.
     * @return the field id.
     */
    public int getFieldId() {
        return fieldId;
    }

    /**
     * Sets the fieldId of the piece.
     * @param fieldId the new fieldId.
     */
    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * Retrieves the team that owns this piece.
     * @return the team.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Gets the walked fields of the piece.
     * @return the walked fields.
     */
    public int getWalkedFields() {
        return walkedFields;
    }

    /**
     * Sets the number of walked fields.
     * @param walkedFields the new number of walkedFields.
     */
    public void setWalkedFields(int walkedFields) {
        this.walkedFields = walkedFields;
    }

    /**
     * Adds a provided number of fields to the walkedFields member.
     * @param amount the amount to add.
     */
    public void addWalkedFields(int amount) {
        walkedFields += amount;
    }


    /**
     * Clones the Spielstein object.
     * @return the clone.
     * @throws CloneNotSupportedException
     */
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
