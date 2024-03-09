package main;

import javax.swing.*;
import java.awt.*;

import levels.CityLevel;
import levels.LevelManager;
import projectiles.ProjectileController;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    static final int origTileSize = 32;
    static final int scale = 4;


    final public static int tileSize = origTileSize * scale; //128x128 tile size


    private Thread gameThread; //makes the game run
    private KeyHandler keyH = new KeyHandler();
    private KeyHandler keyH2 = new KeyHandler();
    private UI ui;


    private int FPS = 60;
    private PlayerOne playerOne;
    private PlayerTwo playerTwo;

    public Image resizedBackground;
    private CharacterSwitcher cs1, cs2;

    private ProjectileController pc1, pc2;
    private HealthManager healthManager;
    private SoundPlayer soundPlayer;
    private boolean canWrite = true;

    //Game state
    public static int gameState;//1==running, 0==paused, -1==over

    public GamePanel(UI givenUi, SoundPlayer sp) {
        //initializes all the elements of the game
        gameState = 1;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setLayout(null);
        LevelManager.setActiveLevel(new CityLevel());
        resizedBackground = LevelManager.getLevelBackground().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_DEFAULT);
        setDoubleBuffered(true);
        cs1 = new CharacterSwitcher(this, 1);
        cs2 = new CharacterSwitcher(this, 2);
        soundPlayer = sp;
        pc1 = new ProjectileController(this, soundPlayer);
        pc2 = new ProjectileController(this, soundPlayer);
        playerOne = new PlayerOne(this, keyH, cs2, pc1, soundPlayer);
        keyH.playerOne = true;
        playerTwo = new PlayerTwo(this, keyH2, cs1, pc2, soundPlayer);
        playerTwo.setPosition(getGameWidth() - 100 - playerTwo.getEnabledEntity().getHITBOX_OFFSET(), 100, "left", 0, 0);
        playerOne.setPosition(100, 100, "left", 0, 0);
        healthManager = new HealthManager(this, playerOne, playerTwo, pc1, pc2, soundPlayer);
        setBackground(new Color(0, 0, 0));
        ui = givenUi;
        ui.initializeGameButtons();
        add(ui.getQuitGameButton());


        addKeyListener(keyH);
        addKeyListener(cs1);
        addKeyListener(keyH2);
        addKeyListener(cs2);
        setFocusable(true);
        setVisible(true);
    }


    public void startGameThread() {
        //starts thread
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

            //if the game is not over
            if (gameState == 1) {
                update();
            }

            //pausing the game
            if (keyH.pauseGame && gameState == 1) {
                soundPlayer.reduceGameSongVolume();
                gameState = 0;
                ui.getQuitButton().setEnabled(true);
                ui.getQuitGameButton().setVisible(true);
            } else if (!keyH.pauseGame && gameState == 0) {
                soundPlayer.increaseGameSongVolume();
                gameState = 1;
                ui.getQuitButton().setEnabled(false);
                ui.getQuitGameButton().setVisible(false);
            }
            repaint();//calls the paintComponent method

            try {
                //deduces the framerate at which it runs
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        //updates every element of the game
        playerOne.update();
        playerTwo.update();
        healthManager.updateHealthBars();
        pc1.updateProjectiles();
        pc2.updateProjectiles();


        healthManager.healthUpdate();


        //on game over
        if (playerOne.getHealth() <= 0) {
            soundPlayer.reduceGameSongVolume();
            gameState = -1;
            ui.getQuitGameButton().setEnabled(true);
            ui.getQuitGameButton().setVisible(true);
        }
        if (playerTwo.getHealth() <= 0) {
            soundPlayer.reduceGameSongVolume();
            gameState = -1;

            ui.getQuitGameButton().setEnabled(true);
            ui.getQuitGameButton().setVisible(true);
        }

    }


    //getters
    public static int getGameWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }


    public static int getGameHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }


    @Override
    public void paintComponent(Graphics g) {
        //draws every component of the game
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(resizedBackground, 0, 0, null);
        playerOne.draw(g2);
        playerTwo.draw(g2);
        pc1.drawProjectiles(g2);
        pc2.drawProjectiles(g2);
        healthManager.drawHealthBarImages(g);
        healthManager.drawHealthBars(g2);
        ui.drawNames(g);

        //on game over
        if (gameState == -1) {
            healthManager.updateHealthBars();
            healthManager.drawHealthBars(g2);

            //which player won
            if (playerOne.getHealth() > playerTwo.getHealth()) {
                ui.drawGameOverScreen(g, ui.getName1(), ui.getName2(), playerOne.getScore(), playerTwo.getScore());
                if (canWrite) {
                    ScoreManager.saveScoreToFile(ui.getName1(), ui.getName2(), playerOne.getScore(), playerTwo.getScore());
                    canWrite = false;
                }
            } else {
                ui.drawGameOverScreen(g, ui.getName2(), ui.getName1(), playerTwo.getScore(), playerOne.getScore());
                if (canWrite) {
                    ScoreManager.saveScoreToFile(ui.getName2(), ui.getName1(), playerTwo.getScore(), playerOne.getScore());
                    canWrite = false;
                }


            }

        }
        if (gameState == 0) {
            ui.drawPauseScreen(g);
        }


    }


}
