package game;

import java.io.Serializable;

public class Feld implements Serializable {
    private float priority;
    private Spielstein occupier;
    private int number;
    private boolean isOccupied;

    public Feld(int number) {
        this.number = number;
        isOccupied = false;
        occupier = null;
    }

    public Spielstein getOccupier() {
        return occupier;
    }

    /**
     * Setzt den Besetzer (Spielstein) für die aktuelle Position.
     * @param occupier Der Spielstein, der die Position besetzen soll. Wird {@code null} angegeben
     * wird die aktuelle Position auf unbesetzt gesetzt und der Besetzer (Spielstein)
     * ist dementsprechend auch {@code null}.
     */
    public void setOccupier(Spielstein occupier) {
        if(occupier == null) {
            isOccupied = false;
            this.occupier = null;
            return;
        }
        isOccupied = true;
        this.occupier = occupier;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Überprüft, ob ein Feld von einem Spielstein besetzt ist.
     * @return {@code true} falls das Feld besetzt ist und {@code false} falls das Feld unbesetzt ist.
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }

    /**
     * Setzt den Zustand, ob die aktuelle Position besetzt ist oder nicht.
     * @param isOccupied Der Zustand, ob die Position besetzt sein soll {@code true} oder nicht {@code false}.
     */
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
}
