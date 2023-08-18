package game;

import java.io.Serializable;

/**
 * Represents the possible states of a piece in the game.
 *
 */
public enum SpielsteinState implements Serializable
{
    /**
     * The game piece is in the home position.
     */
    STATE_HOME,
    /**
     * The game piece is in one of the finish positions of its team.
     */
    STATE_FINISH,
    /**
     * The game piece is currently on the field and neither in the home positions nor the finish positions of the team.
     */
    STATE_PLAYING
}
