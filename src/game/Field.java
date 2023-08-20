package game;

import java.io.Serializable;

public class Field implements Serializable, Cloneable {
    private float priority;
    private Piece occupier;
    private boolean isOccupied;

    public Field() {
        isOccupied = false;
        occupier = null;
    }

    public Piece getOccupier() {
        return occupier;
    }

    /**
     * Setzt den Besetzer (Spielstein) für die aktuelle Position.
     *
     * @param occupier Der Spielstein, der die Position besetzen soll. Wird {@code null} angegeben
     *                 wird die aktuelle Position auf unbesetzt gesetzt und der Besetzer (Spielstein)
     *                 ist dementsprechend auch {@code null}.
     */
    public void setOccupier(Piece occupier) {
        if (occupier == null) {
            isOccupied = false;
            this.occupier = null;
            return;
        }
        isOccupied = true;
        this.occupier = occupier;
    }

    /**
     * Überprüft, ob ein Feld von einem Spielstein besetzt ist.
     *
     * @return {@code true} falls das Feld besetzt ist und {@code false} falls das Feld unbesetzt ist.
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }


    @Override
    public Field clone() throws CloneNotSupportedException {
        Field clone = (Field) super.clone();
        if (occupier != null) {
            clone.occupier = occupier.clone();
        } else {
            clone.occupier = null;
        }
        clone.isOccupied = isOccupied;
        clone.priority = priority;
        return clone;
    }
}
