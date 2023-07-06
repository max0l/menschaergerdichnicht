import java.util.Random;

public class Spiel
{
    void setupPlayers()
    {

    }

    void startGame()
    {
        setupPlayers();

        while(true)
        {
            run();
        }
    }

    void run()
    {

    }

    int rollDice()
    {
        return random.nextInt(6) + 1;
    }

    private Random random = new Random();
}
