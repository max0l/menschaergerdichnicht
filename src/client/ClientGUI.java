package client;

import game.*;
import gui.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class ClientGUI extends JFrame {
    private final RoundButton[] fieldButtons;
    private final RoundButton[][] finnishButtons;
    private final RoundButton[][] homeButtons;
    private final int buttonSize = 70;
    private final int spaceBetween = 70;
    private final JTextField lastDicerollTextField;
    private final JTextField currentlyPlayingTextField;
    private int centerX, centerY;
    private Game game;
    private Color yourColor = null;


    /**
     * Constructor of the ClientGUI class
     *
     * @param game the game object.
     */
    public ClientGUI(Game game) {
        setTitle("Mensch aergere dich nicht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.game = game;

        // Maximize the frame at startup
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(null);

        fieldButtons = new RoundButton[40];
        //generatePlayField();
        homeButtons = new RoundButton[4][4];
        finnishButtons = new RoundButton[4][4];
        currentlyPlayingTextField = new JTextField();
        lastDicerollTextField = new JTextField();


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerX = getWidth() / 2;
                centerY = getHeight() / 2;
                int numButtons = 40;

                currentlyPlayingTextField.setBounds(10, 10, 400, 20);
                lastDicerollTextField.setBounds(10, 50, 200, 20);

                panel.add(currentlyPlayingTextField);
                panel.add(lastDicerollTextField);

                for (int i = 0; i < numButtons; i++) {
                    int x, y;

                    if (i < 5) {
                        x = centerX + spaceBetween;
                        y = (centerY - (spaceBetween * 7)) + (spaceBetween * i);
                    } else if (i < 9) {
                        x = (centerX + spaceBetween) + (spaceBetween * (i - 4));
                        y = (centerY - (spaceBetween * 3));
                    } else if (i < 11) {
                        x = (centerX + (spaceBetween * 5));
                        y = (centerY - (spaceBetween * 3)) + (spaceBetween * (i - 8));
                    } else if (i < 15) {
                        x = (centerX + (spaceBetween * 5)) - (spaceBetween * (i - 10));
                        y = (centerY - spaceBetween);
                    } else if (i < 18) {
                        x = (centerX + spaceBetween);
                        y = (centerY) + (spaceBetween * (i - 15));
                    } else if (i < 21) {
                        x = (centerX + spaceBetween) - (spaceBetween * (i - 18));
                        y = (centerY + (spaceBetween * 3));
                    } else if (i < 25) {
                        x = (centerX - spaceBetween);
                        y = (centerY + (spaceBetween * 7)) - (spaceBetween * (i - 16));
                    } else if (i < 28) {
                        x = (centerX - spaceBetween) - (spaceBetween * (i - 24));
                        y = (centerY - spaceBetween);
                    } else if (i < 31) {
                        x = (centerX - (spaceBetween * 5));
                        y = (centerY - (spaceBetween * 3)) - (spaceBetween * (i - 30));
                    } else if (i < 34) {
                        x = (centerX - (spaceBetween * 5)) + (spaceBetween * (i - 30));
                        y = (centerY - (spaceBetween * 3));
                    } else if (i < 39) {
                        x = (centerX - spaceBetween);
                        y = (centerY + (spaceBetween * 7)) - (spaceBetween * (i - 24));
                    } else {
                        x = (centerX - spaceBetween) + (spaceBetween * (i - 38));
                        y = (centerY - (spaceBetween * 7));
                    }

                    if (fieldButtons[i] == null) {
                        switch (i) {
                            case 0:
                                fieldButtons[i] = new RoundButton(Color.RED);
                                break;
                            case 10:
                                fieldButtons[i] = new RoundButton(Color.BLUE);
                                break;
                            case 20:
                                fieldButtons[i] = new RoundButton(Color.GREEN);
                                break;
                            case 30:
                                fieldButtons[i] = new RoundButton(Color.YELLOW);
                                break;
                            default:
                                fieldButtons[i] = new RoundButton(Color.BLACK);
                                break;
                        }
                        panel.add(fieldButtons[i]);
                    }

                    fieldButtons[i].setBounds(x - 35, y + 80, buttonSize, buttonSize);
                }

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (homeButtons[i][j] == null) {
                            if (i == 0) {
                                homeButtons[i][j] = new RoundButton(Color.RED, true);
                                //finnishButtons[i][j] = new RoundButton(Color.RED);
                            }
                            if (i == 1) {
                                homeButtons[i][j] = new RoundButton(Color.BLUE, true);
                                //finnishButtons[i][j] = new RoundButton(Color.BLUE);
                            }
                            if (i == 2) {
                                homeButtons[i][j] = new RoundButton(Color.GREEN, true);
                                //finnishButtons[i][j] = new RoundButton(Color.GREEN);
                            }
                            if (i == 3) {
                                homeButtons[i][j] = new RoundButton(Color.YELLOW, true);
                                //finnishButtons[i][j] = new RoundButton(Color.YELLOW);
                            }
                            panel.add(homeButtons[i][j]);
                            //panel.add(finnishButtons[i][j]);
                        }
                    }


                }
                //Red Setup
                homeButtons[0][0].setBounds((centerX + (spaceBetween * 5)) - 35, (centerY - (spaceBetween * 7)) + 80, buttonSize, buttonSize);
                homeButtons[0][1].setBounds((centerX + (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 7)) + 80, buttonSize, buttonSize);
                homeButtons[0][2].setBounds((centerX + (spaceBetween * 5)) - 35, (centerY - (spaceBetween * 6)) + 80, buttonSize, buttonSize);
                homeButtons[0][3].setBounds((centerX + (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 6)) + 80, buttonSize, buttonSize);

                //Blue setup
                homeButtons[1][0].setBounds((centerX + (spaceBetween * 4)) - 35, (centerY + (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                homeButtons[1][1].setBounds((centerX + (spaceBetween * 5)) - 35, (centerY + (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                homeButtons[1][2].setBounds((centerX + (spaceBetween * 4)) - 35, (centerY + (spaceBetween * 3)) + 80, buttonSize, buttonSize);
                homeButtons[1][3].setBounds((centerX + (spaceBetween * 5)) - 35, (centerY + (spaceBetween * 3)) + 80, buttonSize, buttonSize);

                //green setup
                homeButtons[2][0].setBounds((centerX - (spaceBetween * 4)) - 35, (centerY + (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                homeButtons[2][1].setBounds((centerX - (spaceBetween * 5)) - 35, (centerY + (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                homeButtons[2][2].setBounds((centerX - (spaceBetween * 4)) - 35, (centerY + (spaceBetween * 3)) + 80, buttonSize, buttonSize);
                homeButtons[2][3].setBounds((centerX - (spaceBetween * 5)) - 35, (centerY + (spaceBetween * 3)) + 80, buttonSize, buttonSize);

                //Yellow Setup
                homeButtons[3][0].setBounds((centerX - (spaceBetween * 5)) - 35, (centerY - (spaceBetween * 7)) + 80, buttonSize, buttonSize);
                homeButtons[3][1].setBounds((centerX - (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 7)) + 80, buttonSize, buttonSize);
                homeButtons[3][2].setBounds((centerX - (spaceBetween * 5)) - 35, (centerY - (spaceBetween * 6)) + 80, buttonSize, buttonSize);
                homeButtons[3][3].setBounds((centerX - (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 6)) + 80, buttonSize, buttonSize);

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (finnishButtons[i][j] == null) {
                            if (i == 0) {
                                //homeButtons[i][j] = new RoundButton(Color.RED, true);
                                finnishButtons[i][j] = new RoundButton(Color.RED);
                            }
                            if (i == 1) {
                                //homeButtons[i][j] = new RoundButton(Color.BLUE, true);
                                finnishButtons[i][j] = new RoundButton(Color.BLUE);
                            }
                            if (i == 2) {
                                //homeButtons[i][j] = new RoundButton(Color.GREEN, true);
                                finnishButtons[i][j] = new RoundButton(Color.GREEN);
                            }
                            if (i == 3) {
                                //homeButtons[i][j] = new RoundButton(Color.YELLOW, true);
                                finnishButtons[i][j] = new RoundButton(Color.YELLOW);
                            }
                            //panel.add(homeButtons[i][j]);
                            panel.add(finnishButtons[i][j]);
                        }
                    }
                }

                //Green
                finnishButtons[3][0].setBounds((centerX - (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[3][1].setBounds((centerX - (spaceBetween * 3)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[3][2].setBounds((centerX - (spaceBetween * 2)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[3][3].setBounds((centerX - (spaceBetween)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);

                //Yellow
                finnishButtons[2][0].setBounds((centerX) - 35, (centerY - (spaceBetween)) + 80, buttonSize, buttonSize);
                finnishButtons[2][1].setBounds((centerX) - 35, (centerY) + 80, buttonSize, buttonSize);
                finnishButtons[2][2].setBounds((centerX) - 35, (centerY + (spaceBetween)) + 80, buttonSize, buttonSize);
                finnishButtons[2][3].setBounds((centerX) - 35, (centerY + (spaceBetween * 2)) + 80, buttonSize, buttonSize);

                //Blue
                finnishButtons[1][0].setBounds((centerX + (spaceBetween)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[1][1].setBounds((centerX + (spaceBetween * 2)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[1][2].setBounds((centerX + (spaceBetween * 3)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);
                finnishButtons[1][3].setBounds((centerX + (spaceBetween * 4)) - 35, (centerY - (spaceBetween * 2)) + 80, buttonSize, buttonSize);

                //Red
                finnishButtons[0][0].setBounds((centerX) - 35, (centerY - (spaceBetween * 6)) + 80, buttonSize, buttonSize);
                finnishButtons[0][1].setBounds((centerX) - 35, (centerY - (spaceBetween * 5)) + 80, buttonSize, buttonSize);
                finnishButtons[0][2].setBounds((centerX) - 35, (centerY - (spaceBetween * 4)) + 80, buttonSize, buttonSize);
                finnishButtons[0][3].setBounds((centerX) - 35, (centerY - (spaceBetween * 3)) + 80, buttonSize, buttonSize);


            }
        });

        setContentPane(panel);
    }


    /**
     * Updates the GUI to the current state of the game
     *
     * @param playingField the current state of the game
     */
    public void updateSpielfeld(PlayingField playingField) {
        for (int i = 0; i < 40; i++) {
            if (playingField.getFeld(i).getOccupier() != null) {
                fieldButtons[i].setSpielstein(playingField.getFeld(i).getOccupier());

                fieldButtons[i].setBackground(playingField.getFeld(i).getOccupier().getColor());
                fieldButtons[i].setEnabled(true);
            } else {
                fieldButtons[i].setSpielstein(playingField.getFeld(i).getOccupier());

                fieldButtons[i].setBackground(Color.WHITE);
                fieldButtons[i].setEnabled(false);
            }
        }

    }

    /**
     * Should return the selected piece of the player, when he clicks on it
     * Unfortunately this does not work yet, because of team members abonding the project
     *
     * @param game the current state of the game
     * @return the selected piece
     */
    public Piece selection(Game game) {
        this.game = game;
        System.out.println("GUI:\t\tTrying to make a selection");
        if (game.getCurrentlyPlaying().getColor() == yourColor) {
            System.out.println("GUI:\t\tPlease select a piece");
            List<Piece> movablePieces = game.selectPiece(game.getCurrentlyPlaying(), game.getLastDiceRoll());
            for (int i = 0; i < 40; i++) {
                if (fieldButtons[i].getSpielstein() != null) {
                    if (movablePieces.contains(fieldButtons[i].getSpielstein())) {
                        fieldButtons[i].setEnabled(true);
                        fieldButtons[i].setOutlineColor(Color.MAGENTA);
                    }
                }

            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (finnishButtons[i][j].getSpielstein() != null) {
                        if (movablePieces.contains(finnishButtons[i][j].getSpielstein())) {
                            finnishButtons[i][j].setEnabled(true);
                            finnishButtons[i][j].setOutlineColor(Color.MAGENTA);
                        }
                    }
                    if (homeButtons[i][j].getSpielstein() != null) {
                        if (movablePieces.contains(homeButtons[i][j].getSpielstein())) {
                            homeButtons[i][j].setEnabled(true);
                            homeButtons[i][j].setOutlineColor(Color.MAGENTA);
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * Updates the GUI to the current state of the game
     *
     * @param game the current state of the game
     */
    public void updateGame(Game game) {
        // Update the GUI components with data from the received game object
        if (game != null) {
            this.game = game;
            updateSpielfeld(game.getSpielfeld());
            for (int i = 0; i < 4; i++) {
                updateHomeFields(game.getTeams().get(i).getSpielsteine(), i);
                updateFinishFields(game.getTeams().get(i).getSpielsteine(), i);
            }
            setCurrentlyPlayingTextField(game.getCurrentlyPlaying());
            lastDicerollTextField.setText("Last Diceroll: " + game.getLastDiceRoll());

        }

    }

    /**
     * Updates the CurrentlyPlayingTextField to the Team that is currently playing
     *
     * @param currentlyPlaying the Team that is currently playing
     */
    private void setCurrentlyPlayingTextField(Team currentlyPlaying) {
        if (currentlyPlaying == null) {
            currentlyPlayingTextField.setText("None is currently playing");
            System.out.println("Currently playing is null");
            return;
        }
        if (currentlyPlaying.getColor() == Color.red) {
            currentlyPlayingTextField.setText("Currently playing: Red");
            System.out.println("Currently playing is red");
        }
        if (currentlyPlaying.getColor() == Color.blue) {
            currentlyPlayingTextField.setText("Currently playing: Blue");
            System.out.println("Currently playing is blue");
        }
        if (currentlyPlaying.getColor() == Color.green) {
            currentlyPlayingTextField.setText("Currently playing: Green");
            System.out.println("Currently playing is green");
        }
        if (currentlyPlaying.getColor() == Color.yellow) {
            currentlyPlayingTextField.setText("Currently playing: Yellow");
            System.out.println("Currently playing is yellow");
        }
    }


    /**
     * Updates the finish fields of each Team
     *
     * @param spielsteine the pieces of the team
     * @param i           the index of the team
     */
    private void updateFinishFields(List<Piece> spielsteine, int i) {
        for (int j = 0; j < 4; j++) {
            if (spielsteine.get(j).getState() == PieceState.STATE_FINISH) {
                finnishButtons[i][j].setSpielstein(spielsteine.get(j));
                finnishButtons[i][j].setInnerColor(spielsteine.get(j).getColor());
                finnishButtons[i][j].setEnabled(true);
            } else {
                finnishButtons[i][j].setSpielstein(null);
                finnishButtons[i][j].setBackground(Color.GRAY);
                finnishButtons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Updates the home fields of each Team
     *
     * @param spielsteine the pieces of the team
     * @param teamID      the index of the team
     */
    private void updateHomeFields(List<Piece> spielsteine, int teamID) {
        for (int i = 0; i < 4; i++) {
            if (spielsteine.get(i).getState() == PieceState.STATE_HOME) {
                homeButtons[teamID][i].setInnerColor(spielsteine.get(i).getColor());
                homeButtons[teamID][i].setEnabled(true);
            } else {
                homeButtons[teamID][i].setBackground(Color.GRAY);
                homeButtons[teamID][i].setEnabled(false);
            }
        }

    }

    /**
     * Sets the color of the player
     *
     * @param yourColor the color of the player
     */
    public void setYourColor(Color yourColor) {
        this.yourColor = yourColor;
    }
}