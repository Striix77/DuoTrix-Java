package main;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel implements Runnable {

    //main menu
    private JButton startGame;
    private Thread menuThread;
    private int FPS = 60;
    private UI ui;
    private SoundPlayer soundPlayer;

    public MainMenu(UI givenUi, SoundPlayer sp) {
        //setting up the background and ui
        menuThread = new Thread(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setLayout(null);
        this.ui = givenUi;
        soundPlayer = sp;
        ui.initializeOpening();
        ui.initializeMainMenu();
        ui.initializeDuotrix();

        setBackground(new Color(0, 0, 0));

        startGame = ui.getSubmitButton();
        add(startGame);
        add(ui.getPlayButton());
        add(ui.getMainOptionsButton());
        add(ui.getQuitButton());
        add(ui.getBackButton());
        add(ui.getScoreButton());
        add(ui.getText1());
        add(ui.getText2());
        add(ui.getScorePane());


        setVisible(true);
    }

    //starts the thread
    public void startMainMenuThread() {
        menuThread.start();
    }

    public JButton getStartGameButton() {
        return startGame;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (menuThread != null) {


            update();


            repaint();//calls the paintComponent method

            try {
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

    private void update() {
        //updates the UI
        ui.updateOpening();
        ui.updateBackground();
        ui.updateRotation();

    }

    @Override
    protected void paintComponent(Graphics g) {
        //draws the UI
        super.paintComponent(g);
        ui.drawOpening(g);
        ui.drawBackground(g);
        ui.drawRotation(g);
        if (!ui.isPlayable()) {
            ui.drawNameError(g);
        }
        if (ui.getNameSelector()) {
            ui.drawNameSelector(g);
        }
    }

    public void stopThread() {
        menuThread = null;
    }


}
