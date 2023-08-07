import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static Spiel spiel = null;
    public static void main(String[] args)
    {
        while(spiel == null)
        {
            askForLoad();
        }

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
            File saveFile = null;

            List<Path> saveGameFiles = findSaveGameFiles(Path.of(persistentDirPath));

            if (saveGameFiles.isEmpty()) {
                System.out.println("No save game files found.");
                spiel = new Spiel();
                System.out.println("New Game created!");
            } else {
                System.out.println("Select a save game file:");
                for (int i = 0; i < saveGameFiles.size(); i++) {
                    System.out.println(i + ": " + saveGameFiles.get(i).getFileName());
                }

                Scanner scanner = new Scanner(System.in);
                int selection = scanner.nextInt();

                if (selection >= 0 && selection < saveGameFiles.size()) {
                    Path selectedFile = saveGameFiles.get(selection);
                    System.out.println("Selected file: " + selectedFile.getFileName());
                    saveFile = new File(selectedFile.toString());
                    filePath = selectedFile.toString();
                } else {
                    System.out.println("Invalid selection.");
                }
            }

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

    public static List<Path> findSaveGameFiles(Path directory) {
        List<Path> saveGameFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "save_game*")) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    saveGameFiles.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saveGameFiles;
    }

}
