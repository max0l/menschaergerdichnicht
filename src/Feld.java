import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Feld implements Serializable {

    private int id;
    private int buttonNumber;
    private Object feldObject;
    private float priority;
    private Spielstein occupier;
    private int number;
    private boolean isOccupied;
    private JButton associatedButton;


    public Feld(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

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

    public int getId() {
        return id;
    }
    public int getButtonNumber() {
        return buttonNumber;
    }
    public Object getFeldObject() {
        return feldObject;
    }

    public Feld(int id, int buttonNumber, JButton associatedButton) {
        this.id = id;
        this.buttonNumber = buttonNumber;
        this.associatedButton = associatedButton;
    }


    public void setFeldObject(Object feldObject) {
        this.feldObject = feldObject;
    }
}
