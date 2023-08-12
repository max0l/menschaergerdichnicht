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

    public boolean getIsOccupied() {
        return isOccupied;
    }

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
