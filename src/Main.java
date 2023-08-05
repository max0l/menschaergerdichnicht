import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main
{
    public static void main(String[] args)
    {
        //new Spiel().startGame();

        String saveGamePath = "C:\\Users\\v1ce\\IdeaProjects\\menschaergerdichnicht\\out\\production\\menschaergerdichnicht\\saves\\save_game";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveGamePath))) {
            Spiel loadedGameObject = (Spiel) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
