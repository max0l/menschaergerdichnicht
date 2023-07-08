import java.awt.*;

public class Feld {
    private float priority;
    private Spielstein occupier;
    private int number;
    private boolean isOccupied;

    public Feld(int number) {
        this.number = number;
    }

    public Spielstein getOccupier() {
        return occupier;
    }

    public void setOccupier(Spielstein occupier) {
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
