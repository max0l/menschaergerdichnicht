import java.io.*;
public class Main
{
    private static Spiel spiel = null;
    private static SpielfeldGui spielfeldGui = null;
    public static void main(String[] args)
    {

        while(spiel == null)
        {
            askForLoad();
        }
        Menue.Gui(args);

        spiel.startGame();
    }


    static void askForLoad()
    {
        char input;
        System.out.println("Do you want to load a existing game? (y/n)");

        try
        {
            input = (char) System.in.read();
            System.in.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        if(input == 'y')
        {
            String userHome = System.getProperty("user.home");

            String persistentDirPath;
            if (System.getProperty("os.name").toLowerCase().contains("win"))
            {
                String appData = System.getenv("APPDATA");
                persistentDirPath = appData + File.separator + "menschaergerdichnicht";
            }
            else
            {
                persistentDirPath = userHome + File.separator + ".menschaergerdichnicht";
            }

            String fileName = "save_game";
            String filePath = persistentDirPath + File.separator + fileName;

            File saveFile = new File(filePath);

            if (!saveFile.exists())
            {
                System.err.println("No save game available!");
                return;
            }

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath)))
            {
                spiel = (Spiel) inputStream.readObject();
                System.out.println("Save Game loaded!");
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else if(input == 'n')
        {
            spiel = new Spiel();
            System.out.println("New Game created!");
        }
        else
        {
            System.err.println("Invalid input");
        }
    }

}
