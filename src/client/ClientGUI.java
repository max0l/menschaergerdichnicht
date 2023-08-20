package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import game.*;
import gui.RoundButton;

public class ClientGUI extends JFrame {
    private final RoundButton[] fieldButtons;
    private final RoundButton[][] finnishButtons;
    private final RoundButton[][] homeButtons;
    private JPanel panel;
    private int centerX, centerY;
    private final int buttonSize = 70;
    private final int spaceBetween = 70;
    private Spiel spiel;
    private final JTextField lastDicerollTextField;
    private final JTextField currentlyPlayingTextField;
    private Color yourColor = null;


    public ClientGUI(Spiel spiel) {
        setTitle("Mensch aergere dich nicht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.spiel = spiel;

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
                        x = (centerX + spaceBetween) + (spaceBetween * (i-4));
                        y = (centerY - (spaceBetween * 3));
                    } else if (i < 11) {
                        x = (centerX + (spaceBetween * 5));
                        y = (centerY - (spaceBetween * 3)) + (spaceBetween * (i - 8));
                    } else if(i < 15) {
                        x = (centerX + (spaceBetween * 5)) - (spaceBetween * (i - 10));
                        y = (centerY-spaceBetween);
                    } else if(i < 18) {
                        x = (centerX + spaceBetween);
                        y = (centerY) + (spaceBetween * (i - 15));
                    } else if(i < 21) {
                        x = (centerX + spaceBetween) - (spaceBetween * (i - 18));
                        y = (centerY + (spaceBetween * 3));
                    } else if(i < 25) {
                        x = (centerX - spaceBetween);
                        y = (centerY + (spaceBetween*7)) - (spaceBetween * (i - 16));
                    } else if(i < 28) {
                        x = (centerX - spaceBetween) - (spaceBetween * (i - 24));
                        y = (centerY - spaceBetween);
                    } else if(i < 31) {
                        x = (centerX - (spaceBetween * 5));
                        y = (centerY - (spaceBetween * 3)) - (spaceBetween * (i - 30));
                    } else if(i < 34) {
                        x = (centerX - (spaceBetween * 5)) + (spaceBetween * (i - 30));
                        y = (centerY - (spaceBetween * 3));
                    } else if(i < 39) {
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

                    fieldButtons[i].setBounds(x-35, y+80, buttonSize, buttonSize);
                }

                for(int i = 0; i<4; i++){
                    for(int j = 0; j<4; j++){
                        if(homeButtons[i][j] == null) {
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
                homeButtons[0][0].setBounds((centerX+(spaceBetween*5))-35, (centerY-(spaceBetween*7))+80, buttonSize, buttonSize);
                homeButtons[0][1].setBounds((centerX+(spaceBetween*4))-35, (centerY-(spaceBetween*7))+80, buttonSize, buttonSize);
                homeButtons[0][2].setBounds((centerX+(spaceBetween*5))-35, (centerY-(spaceBetween*6))+80, buttonSize, buttonSize);
                homeButtons[0][3].setBounds((centerX+(spaceBetween*4))-35, (centerY-(spaceBetween*6))+80, buttonSize, buttonSize);

                //Blue setup
                homeButtons[1][0].setBounds((centerX+(spaceBetween*4))-35, (centerY+(spaceBetween*2))+80, buttonSize, buttonSize);
                homeButtons[1][1].setBounds((centerX+(spaceBetween*5))-35, (centerY+(spaceBetween*2))+80, buttonSize, buttonSize);
                homeButtons[1][2].setBounds((centerX+(spaceBetween*4))-35, (centerY+(spaceBetween*3))+80, buttonSize, buttonSize);
                homeButtons[1][3].setBounds((centerX+(spaceBetween*5))-35, (centerY+(spaceBetween*3))+80, buttonSize, buttonSize);

                //green setup
                homeButtons[2][0].setBounds((centerX-(spaceBetween*4))-35, (centerY+(spaceBetween*2))+80, buttonSize, buttonSize);
                homeButtons[2][1].setBounds((centerX-(spaceBetween*5))-35, (centerY+(spaceBetween*2))+80, buttonSize, buttonSize);
                homeButtons[2][2].setBounds((centerX-(spaceBetween*4))-35, (centerY+(spaceBetween*3))+80, buttonSize, buttonSize);
                homeButtons[2][3].setBounds((centerX-(spaceBetween*5))-35, (centerY+(spaceBetween*3))+80, buttonSize, buttonSize);

                //Yellow Setup
                homeButtons[3][0].setBounds((centerX-(spaceBetween*5))-35, (centerY-(spaceBetween*7))+80, buttonSize, buttonSize);
                homeButtons[3][1].setBounds((centerX-(spaceBetween*4))-35, (centerY-(spaceBetween*7))+80, buttonSize, buttonSize);
                homeButtons[3][2].setBounds((centerX-(spaceBetween*5))-35, (centerY-(spaceBetween*6))+80, buttonSize, buttonSize);
                homeButtons[3][3].setBounds((centerX-(spaceBetween*4))-35, (centerY-(spaceBetween*6))+80, buttonSize, buttonSize);

                for(int i = 0; i<4; i++){
                    for(int j = 0; j<4; j++){
                        if(finnishButtons[i][j] == null) {
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
                finnishButtons[3][0].setBounds((centerX-(spaceBetween*4))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[3][1].setBounds((centerX-(spaceBetween*3))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[3][2].setBounds((centerX-(spaceBetween*2))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[3][3].setBounds((centerX-(spaceBetween))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);

                //Yellow
                finnishButtons[2][0].setBounds((centerX)-35, (centerY-(spaceBetween))+80, buttonSize, buttonSize);
                finnishButtons[2][1].setBounds((centerX)-35, (centerY)+80, buttonSize, buttonSize);
                finnishButtons[2][2].setBounds((centerX)-35, (centerY+(spaceBetween))+80, buttonSize, buttonSize);
                finnishButtons[2][3].setBounds((centerX)-35, (centerY+(spaceBetween*2))+80, buttonSize, buttonSize);

                //Blue
                finnishButtons[1][0].setBounds((centerX+(spaceBetween))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[1][1].setBounds((centerX+(spaceBetween*2))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[1][2].setBounds((centerX+(spaceBetween*3))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);
                finnishButtons[1][3].setBounds((centerX+(spaceBetween*4))-35, (centerY-(spaceBetween*2))+80, buttonSize, buttonSize);

                //Red
                finnishButtons[0][0].setBounds((centerX)-35, (centerY-(spaceBetween*6))+80, buttonSize, buttonSize);
                finnishButtons[0][1].setBounds((centerX)-35, (centerY-(spaceBetween*5))+80, buttonSize, buttonSize);
                finnishButtons[0][2].setBounds((centerX)-35, (centerY-(spaceBetween*4))+80, buttonSize, buttonSize);
                finnishButtons[0][3].setBounds((centerX)-35, (centerY-(spaceBetween*3))+80, buttonSize, buttonSize);


            }
        });

        setContentPane(panel);
    }


    public void updateSpielfeld(Spielfeld spielfeld) {
        for (int i = 0; i < 40; i++) {
            if (spielfeld.getFeld(i).getOccupier() != null) {
                fieldButtons[i].setSpielstein(spielfeld.getFeld(i).getOccupier());;
                fieldButtons[i].setBackground(spielfeld.getFeld(i).getOccupier().getColor());
                fieldButtons[i].setEnabled(true);
            } else {
                fieldButtons[i].setSpielstein(spielfeld.getFeld(i).getOccupier());;
                fieldButtons[i].setBackground(Color.WHITE);
                fieldButtons[i].setEnabled(false);
            }
        }

    }

    public Spielstein selection(Spiel spiel) {
        this.spiel = spiel;
        System.out.println("GUI:\t\tTrying to make a selection");
        if(spiel.getCurrentlyPlaying().getColor() == yourColor) {
            System.out.println("GUI:\t\tPlease select a piece");
            List<Spielstein> movablePieces = spiel.selectPiece(spiel.getCurrentlyPlaying(), spiel.getLastDiceRoll());
            for(int i = 0; i<40;i++){
                if(fieldButtons[i].getSpielstein() != null){
                    if(movablePieces.contains(fieldButtons[i].getSpielstein())){
                        fieldButtons[i].setEnabled(true);
                        fieldButtons[i].setOutlineColor(Color.MAGENTA);
                    }
                }

            }
            for(int i = 0; i<4; i++){
                for(int j = 0; j<4; j++){
                    if(finnishButtons[i][j].getSpielstein() != null) {
                        if(movablePieces.contains(finnishButtons[i][j].getSpielstein())){
                            finnishButtons[i][j].setEnabled(true);
                            finnishButtons[i][j].setOutlineColor(Color.MAGENTA);
                        }
                    }
                    if(homeButtons[i][j].getSpielstein() != null) {
                        if(movablePieces.contains(homeButtons[i][j].getSpielstein())){
                            homeButtons[i][j].setEnabled(true);
                            homeButtons[i][j].setOutlineColor(Color.MAGENTA);
                        }
                    }

                }
            }
        }
        return null;
    }

    public void updateGame(Spiel spiel) {
        // Update the GUI components with data from the received game object
        if(spiel != null) {
            this.spiel = spiel;
            updateSpielfeld(spiel.getSpielfeld());
            for(int i = 0; i<4; i++){
                updateHomeFields(spiel.getTeams().get(i).getSpielsteine(), i);
                updateFinishFields(spiel.getTeams().get(i).getSpielsteine(), i);
            }
            setCurrentlyPlayingTextField(spiel.getCurrentlyPlaying());
            lastDicerollTextField.setText("Last Diceroll: " + spiel.getLastDiceRoll());

        }

    }

    private void setCurrentlyPlayingTextField(Team currentlyPlaying) {
        if(currentlyPlaying == null) {
            currentlyPlayingTextField.setText("None is currently playing");
            System.out.println("Currently playing is null");
            return;
        }
        if(currentlyPlaying.getColor() == Color.red) {
            currentlyPlayingTextField.setText("Currently playing: Red");
            System.out.println("Currently playing is red");
        }
        if(currentlyPlaying.getColor() == Color.blue) {
            currentlyPlayingTextField.setText("Currently playing: Blue");
            System.out.println("Currently playing is blue");
        }
        if(currentlyPlaying.getColor() == Color.green) {
            currentlyPlayingTextField.setText("Currently playing: Green");
            System.out.println("Currently playing is green");
        }
        if(currentlyPlaying.getColor() == Color.yellow) {
            currentlyPlayingTextField.setText("Currently playing: Yellow");
            System.out.println("Currently playing is yellow");
        }
    }


    private void updateFinishFields(List<Spielstein> spielsteine, int i) {
        for (int j = 0; j < 4; j++) {
            if (spielsteine.get(j).getState() == SpielsteinState.STATE_FINISH) {
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

    private void updateHomeFields(List<Spielstein> spielsteine, int teamID) {
        for (int i = 0; i < 4; i++) {
            if (spielsteine.get(i).getState() == SpielsteinState.STATE_HOME) {
                homeButtons[teamID][i].setInnerColor(spielsteine.get(i).getColor());
                homeButtons[teamID][i].setEnabled(true);
            } else {
                homeButtons[teamID][i].setBackground(Color.GRAY);
                homeButtons[teamID][i].setEnabled(false);
            }
        }

    }

    public void setYourColor(Color yourColor) {
        this.yourColor = yourColor;
    }
}
