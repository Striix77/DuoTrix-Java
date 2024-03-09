package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class UI {
    //all UI elements

    //all images
    private ArrayList<BufferedImage> background;
    private ArrayList<BufferedImage> duotrixOpeningImg;
    private ArrayList<BufferedImage> duotrixRotating;

    //custom font
    private final Font customBitFont;

    //buttons
    private JButton playButton;
    private JButton mainOptions;
    private JButton quitButton;
    private JButton quitGameButton;
    private JButton backButton;
    private JButton submitButton;
    private JButton scoresButton;

    //helpful variables for images, rotating elements,
    private boolean playBackgroundForward = false;
    private boolean playBackgroundBackward = false;
    private int backCounter = 0;
    private int backNumber = 0;
    private int duotrixOpeningCounter = -100;
    private int duotrixOpeningNumber = 0;
    private int duotrixRotationCounter = 0;
    private int duotrixRotationNumber = 0;
    private boolean dir = true;
    private boolean opening = true;
    private boolean rotating = false;
    private boolean playRotationForward = true;
    private boolean afterPlay = false;
    //custom colors
    private Color buttonColor;
    private Color buttonForeground;

    //text boxes
    private JTextField text1;
    private JTextField text2;
    private String name1 = "";
    private String name2 = "";
    private boolean playable = true;
    private JTextArea scoreArea;
    private JScrollPane scorePane;
    private final SoundPlayer soundPlayer;
    private boolean nameSelector = false;

    public UI(SoundPlayer sp) {
        //constructor
        soundPlayer = sp;
        try {
            //loading font
            customBitFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\8-BIT WONDER.TTF")).deriveFont(12f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customBitFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeOpening() {
        //reads the opening scene's images
        duotrixOpeningImg = new ArrayList<>();
        try {
            for (int i = 0; i < 24; i++) {
                duotrixOpeningImg.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/DuotrixOpening" + i + ".png"))));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDuotrix() {
        //reads the images for the duotrix
        duotrixRotating = new ArrayList<>();
        try {
            for (int i = 0; i < 8; i++) {
                duotrixRotating.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/DuotrixRotateClockwise" + i + ".png"))));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeMainMenu() {
        //initializes all the components of the main menu
        background = new ArrayList<>();
        try {
            for (int i = 0; i < 25; i++) {
                background.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/MainBackground" + i + ".png"))));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //declaring button and setting bounds
        playButton = new JButton("Play");
        playButton.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 - 25 - 50, 200, 50);
        mainOptions = new JButton("Options");
        mainOptions.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 - 25, 200, 50);
        quitButton = new JButton("Quit");
        quitButton.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 - 25 + 50, 200, 50);
        backButton = new JButton("Back");
        backButton.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 - 25 + 200, 200, 50);
        scoresButton = new JButton("Recent players");
        scoresButton.setBounds(GamePanel.getGameWidth() / 2 - 175, GamePanel.getGameHeight() / 2 - 25 + 150, 350, 50);
        submitButton = new JButton("Play");
        submitButton.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 - 25 + 150, 200, 50);

        //setting the font
        playButton.setFont(customBitFont.deriveFont(25f));
        mainOptions.setFont(customBitFont.deriveFont(25f));
        quitButton.setFont(customBitFont.deriveFont(25f));
        backButton.setFont(customBitFont.deriveFont(25f));
        scoresButton.setFont(customBitFont.deriveFont(25f));
        submitButton.setFont(customBitFont.deriveFont(25f));

        //custom colors and button desings
        buttonColor = new Color(0, 0, 0, 0);
        playButton.setBorder(BorderFactory.createEmptyBorder());
        mainOptions.setBorder(BorderFactory.createEmptyBorder());
        quitButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBorder(BorderFactory.createEmptyBorder());
        scoresButton.setBorder(BorderFactory.createEmptyBorder());
        submitButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setBackground(buttonColor);
        mainOptions.setBackground(buttonColor);
        quitButton.setBackground(buttonColor);
        backButton.setBackground(buttonColor);
        scoresButton.setBackground(buttonColor);
        submitButton.setBackground(buttonColor);
        buttonForeground = new Color(16, 26, 47);
        playButton.setForeground(buttonForeground);
        mainOptions.setForeground(buttonForeground);
        quitButton.setForeground(buttonForeground);
        backButton.setForeground(new Color(91, 184, 201));
        scoresButton.setForeground(new Color(91, 184, 201));
        submitButton.setForeground(new Color(91, 184, 201));

        //eliminites ugly square around the text
        playButton.setFocusable(false);
        mainOptions.setFocusable(false);
        quitButton.setFocusable(false);
        backButton.setFocusable(false);
        scoresButton.setFocusable(false);
        submitButton.setFocusable(false);
        backButton.setEnabled(false);
        scoresButton.setEnabled(false);
        submitButton.setEnabled(false);
        backButton.setVisible(false);
        scoresButton.setVisible(false);
        submitButton.setVisible(false);

        //initializing the text boxes for the names
        text1 = new JTextField();
        text1.setBounds(GamePanel.getGameWidth() / 2 - 150 - 250, GamePanel.getGameHeight() / 2 - 50, 300, 100);
        text1.setVisible(false);
        text1.setEnabled(false);
        text2 = new JTextField();
        text2.setBounds(GamePanel.getGameWidth() / 2 - 150 + 250, GamePanel.getGameHeight() / 2 - 50, 300, 100);
        text2.setVisible(false);
        text2.setEnabled(false);
        text1.setFont(customBitFont.deriveFont(27f));
        text2.setFont(customBitFont.deriveFont(27f));
        text1.setBackground(new Color(0, 183, 255, 190));
        text2.setBackground(new Color(0, 183, 255, 190));
        text1.setForeground(buttonForeground);
        text2.setForeground(buttonForeground);
        text1.setBorder(BorderFactory.createEmptyBorder());
        text2.setBorder(BorderFactory.createEmptyBorder());


        //recent players and scores
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(customBitFont.deriveFont(30f));
        scoreArea.setBounds(100, 100, GamePanel.getGameWidth() - 200, GamePanel.getGameHeight() - 300);
        scorePane = new JScrollPane(scoreArea);
        scorePane.setBounds(100, 100, GamePanel.getGameWidth() - 200, GamePanel.getGameHeight() - 400);
        scorePane.setFont(customBitFont.deriveFont(30f));
        scorePane.setBackground(new Color(0, 183, 255, 190));
        scoreArea.setBackground(new Color(0, 183, 255, 190));
        scoreArea.setForeground(buttonForeground);
        scorePane.setForeground(buttonForeground);
        scorePane.setBorder(BorderFactory.createEmptyBorder());

        //loading scores from scores file
        ScoreManager.loadScoreFromFile(scoreArea);


        //designing the buttons
        playButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //rotates the duotrix
                if (dir) {
                    playBackgroundForward = true;
                    playBackgroundBackward = false;

                } else {

                    playBackgroundForward = false;
                    playBackgroundBackward = true;
                }
                dir = !dir;
                //disables all the unnecessary UI elements
                nameSelector = true;
                text1.setVisible(true);
                text1.setEnabled(true);
                text2.setVisible(true);
                text2.setEnabled(true);
                backButton.setEnabled(true);
                backButton.setVisible(true);
                submitButton.setEnabled(true);
                submitButton.setVisible(true);
                mainOptions.setEnabled(false);
                mainOptions.setVisible(false);
                quitButton.setEnabled(false);
                quitButton.setVisible(false);
                afterPlay = true;
                playButton.setVisible(false);
                playButton.setEnabled(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                soundPlayer.playButtonBeep();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rotating = true;
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(buttonForeground);
            }
        });

        mainOptions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //rotates the duotrix
                if (dir) {
                    playBackgroundForward = true;
                    playBackgroundBackward = false;

                } else {

                    playBackgroundForward = false;
                    playBackgroundBackward = true;
                }
                dir = !dir;
                //disables unnecessary elements
                backButton.setEnabled(true);
                backButton.setVisible(true);
                scoresButton.setEnabled(true);
                scoresButton.setVisible(true);
                quitButton.setEnabled(false);
                quitButton.setVisible(false);
                afterPlay = true;
                playButton.setVisible(false);
                playButton.setEnabled(false);
                mainOptions.setEnabled(false);
                mainOptions.setVisible(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                soundPlayer.playButtonBeep();
                //playBackgroundForward =false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rotating = true;
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //rotating=false;
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(buttonForeground);
            }
        });

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //exists the game
                System.exit(0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                soundPlayer.playButtonBeep();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rotating = true;
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(buttonForeground);
            }
        });


        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //rotates the duotrix
                if (dir) {
                    playBackgroundForward = true;
                    playBackgroundBackward = false;

                } else {

                    playBackgroundForward = false;
                    playBackgroundBackward = true;
                }
                dir = !dir;

                playable = true;
                //unnecessary elements
                nameSelector = false;
                text1.setVisible(false);
                text1.setEnabled(false);
                text2.setVisible(false);
                text2.setEnabled(false);
                mainOptions.setEnabled(true);
                mainOptions.setVisible(true);
                quitButton.setEnabled(true);
                quitButton.setVisible(true);
                afterPlay = false;
                playButton.setVisible(true);
                playButton.setEnabled(true);
                submitButton.setVisible(false);
                submitButton.setEnabled(false);
                scoresButton.setVisible(false);
                scoresButton.setEnabled(false);
                scorePane.setEnabled(false);
                scorePane.setVisible(false);
                scoreArea.setEnabled(false);
                scoreArea.setVisible(false);
                backButton.setVisible(false);
                backButton.setEnabled(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                soundPlayer.playButtonBeep();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(new Color(91, 184, 201));

            }
        });

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                name1 = text1.getText();
                name2 = text2.getText();
                nameSelector = false;
                playable = name1.length() <= 10 && name2.length() <= 10 && !name1.isEmpty() && !name2.isEmpty();


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                soundPlayer.playButtonBeep();
                if(playable) {
                    soundPlayer.startGameMusic();
                    soundPlayer.stopMenuMusic();
                }
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(new Color(91, 184, 201));

            }
        });

        scoresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (dir) {
                    playBackgroundForward = true;
                    playBackgroundBackward = false;

                } else {

                    playBackgroundForward = false;
                    playBackgroundBackward = true;
                }
                dir = !dir;
                playable = true;
                scorePane.setEnabled(true);
                scorePane.setVisible(true);
                scoreArea.setEnabled(true);
                scoreArea.setVisible(true);
                scoresButton.setVisible(false);
                scoresButton.setEnabled(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                soundPlayer.playButtonBeep();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(new Color(91, 184, 201));

            }
        });

        //intially, they're all disabled
        playButton.setEnabled(false);
        mainOptions.setEnabled(false);
        quitButton.setEnabled(false);
        playButton.setVisible(false);
        mainOptions.setVisible(false);
        quitButton.setVisible(false);
        scoreArea.setEnabled(false);
        scoreArea.setVisible(false);
        scorePane.setEnabled(false);
        scorePane.setVisible(false);
    }

    public void initializeGameButtons() {
        //initializes the in game button
        buttonColor = new Color(0, 0, 0, 0);
        buttonForeground = new Color(16, 26, 47);
        quitGameButton = new JButton("Quit");
        quitGameButton.setBounds(GamePanel.getGameWidth() / 2 - 100, GamePanel.getGameHeight() / 2 + 200, 200, 50);
        quitGameButton.setFont(customBitFont.deriveFont(25f));
        quitGameButton.setBorder(BorderFactory.createEmptyBorder());
        quitGameButton.setBackground(buttonColor);
        quitGameButton.setForeground(buttonForeground);
        quitGameButton.setFocusable(false);
        quitGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                soundPlayer.playButtonBeep();
                System.exit(0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setBackground(new Color(0, 75, 164));
                e.getComponent().setForeground(new Color(2, 192, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setBackground(buttonColor);
                e.getComponent().setForeground(buttonForeground);
            }
        });

        quitGameButton.setEnabled(false);
        quitGameButton.setVisible(false);
    }

    public boolean getNameSelector() {
        return nameSelector;
    }


    //getters, setters
    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getMainOptionsButton() {
        return mainOptions;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getScoreButton() {
        return scoresButton;
    }

    public JScrollPane getScorePane() {
        return scorePane;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public boolean isPlayable() {
        return playable;
    }

    public JButton getQuitGameButton() {
        return quitGameButton;
    }

    public JTextField getText1() {
        return text1;
    }

    public JTextField getText2() {
        return text2;
    }

    public boolean isOpening() {
        return opening;
    }

    public void updateOpening() {
        //updates the opening sequence
        if (opening) {

            duotrixOpeningCounter++;
            if (duotrixOpeningCounter > 4) {
                if (duotrixOpeningNumber == 23) {

                    try {
                        soundPlayer.startMenuMusic();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    opening = false;
                    playButton.setEnabled(true);
                    mainOptions.setEnabled(true);
                    quitButton.setEnabled(true);
                    playButton.setVisible(true);
                    mainOptions.setVisible(true);
                    quitButton.setVisible(true);
                } else {
                    duotrixOpeningNumber++;
                }
                duotrixOpeningCounter = 0;
            }
        }
    }

    public void drawOpening(Graphics g) {
        //draws the opening sequence
        if (opening) {
            BufferedImage image;
            Graphics2D g2 = (Graphics2D) g;
            if (duotrixOpeningCounter <= 0) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (duotrixOpeningCounter + 100) / 100));
            }
            image = duotrixOpeningImg.get(duotrixOpeningNumber);
            g2.drawImage(image, GamePanel.getGameWidth() / 2 - GamePanel.tileSize * 5 / 2, GamePanel.getGameHeight() / 2 - GamePanel.tileSize * 5 / 2, GamePanel.tileSize * 5, GamePanel.tileSize * 5, null);
        }
    }

    public void updateBackground() {
        //updates the moving background
        if (!opening) {
            if (playBackgroundForward) {
                backCounter++;
                if (backCounter > 1) {
                    if (backNumber == 24) {
                        //backNumber=0;
                        playBackgroundForward = false;
                    } else {
                        backNumber++;
                    }
                    backCounter = 0;
                }
            }
            if (playBackgroundBackward) {
                backCounter++;
                if (backCounter > 1) {
                    if (backNumber == 0) {
                        //backNumber=0;
                        playBackgroundBackward = false;
                    } else {
                        backNumber--;
                    }
                    backCounter = 0;
                }
            }
        }
    }

    public void drawBackground(Graphics g) {
        //draws the background
        if (!opening) {
            BufferedImage image = null;
            image = background.get(backNumber);
            g.drawImage(image, 0, 0, null);
        }
    }

    public void updateRotation() {
        //updates the rotating dial on the duotrix
        if (rotating) {
            if (playRotationForward) {
                duotrixRotationCounter++;
                if (duotrixRotationCounter > 1) {
                    if (duotrixRotationNumber == 7) {
                        //backNumber=0;
                        rotating = false;
                        playRotationForward = false;
                    } else {
                        duotrixRotationNumber++;
                    }
                    duotrixRotationCounter = 0;
                }
            } else {
                duotrixRotationCounter++;
                if (duotrixRotationCounter > 1) {
                    if (duotrixRotationNumber == 0) {
                        //backNumber=0;
                        rotating = false;
                        playRotationForward = true;
                    } else {
                        duotrixRotationNumber--;
                    }
                    duotrixRotationCounter = 0;
                }
            }
        }
    }

    public void drawRotation(Graphics g) {
        //draws the duotrix rotating
        if (!opening && !afterPlay) {
            BufferedImage image;
            image = duotrixRotating.get(duotrixRotationNumber);
            g.drawImage(image, GamePanel.getGameWidth() / 2 - GamePanel.tileSize * 5 / 2, GamePanel.getGameHeight() / 2 - GamePanel.tileSize * 5 / 2, GamePanel.tileSize * 5, GamePanel.tileSize * 5, null);
        }
    }


    public void drawGameOverScreen(Graphics g, String winner, String loser, int score1, int score2) {

        //in-game game over screen
        g.setColor(new Color(0, 0, 0, 0.3f));
        g.fillRect(0, 0, GamePanel.getGameWidth(), GamePanel.getGameHeight());
        g.setFont(customBitFont.deriveFont(36f));
        g.setColor(new Color(0, 215, 255));
        String gameOver = "Game Over";
        g.drawString(gameOver, GamePanel.getGameWidth() / 2 - g.getFontMetrics().stringWidth(gameOver) / 2, GamePanel.getGameHeight() / 3);
        String winnerScore = winner + " won with " + score1 + " points";
        String loserScore = loser + " lost with " + score2 + " points";

        g.drawString(winnerScore, GamePanel.getGameWidth() / 2 - g.getFontMetrics().stringWidth(winnerScore) / 2, GamePanel.getGameHeight() / 2);
        g.drawString(loserScore, GamePanel.getGameWidth() / 2 - g.getFontMetrics().stringWidth(loserScore) / 2, GamePanel.getGameHeight() / 2 + 50);
    }

    public void drawNames(Graphics g) {
        //draws names above the health bars
        g.setFont(customBitFont.deriveFont(30f));
        g.setColor(new Color(0, 22, 28));
        g.drawString(name1, 140, 40);
        g.drawString(name2, GamePanel.getGameWidth() - 140 - g.getFontMetrics().stringWidth(name2), 40);
    }

    public void drawNameSelector(Graphics g) {
        g.setFont(customBitFont.deriveFont(30f));
        g.setColor(new Color(0, 215, 255));
        g.drawString("Enter your names", GamePanel.getGameWidth() / 2 - 150 - 250, GamePanel.getGameHeight() / 2 - 60);
    }

    public void drawNameError(Graphics g) {
        //wrong name error
        g.setFont(customBitFont.deriveFont(14f));
        g.setColor(new Color(166, 13, 49));
        String nameError = "The names must have between 1 and 10 characters";
        g.drawString(nameError, GamePanel.getGameWidth() / 2 - g.getFontMetrics().stringWidth(nameError) / 2, GamePanel.getGameHeight() / 2 - 25 + 110);
    }

    public void drawPauseScreen(Graphics g) {
        //draws pause screen on ESC
        g.setColor(new Color(0, 0, 0, 0.2f));
        g.fillRect(0, 0, GamePanel.getGameWidth(), GamePanel.getGameHeight());
        g.setFont(customBitFont.deriveFont(36f));
        g.setColor(new Color(0, 215, 255));
        String gameOver = "Game Paused";
        g.drawString(gameOver, GamePanel.getGameWidth() / 2 - g.getFontMetrics().stringWidth(gameOver) / 2, GamePanel.getGameHeight() / 2);

    }
}
