package game;

import java.io.Serializable;

public enum SpielsteinState implements Serializable
{
    STATE_HOME, STATE_FINISH, STATE_PLAYING
    //game.Spielstein ist entweder zu Hause, im Ziel oder auf dem game.Feld
}
