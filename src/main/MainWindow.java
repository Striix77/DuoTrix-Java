package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    //main window of the game that contains the game panel and the main menu and switches between them
    private final UI ui;
    private SoundPlayer soundPlayer;

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setTitle("Tritrix");
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        soundPlayer = new SoundPlayer();
        ui = new UI(soundPlayer);
        cardPanel.setLayout(cardLayout);
        add(cardPanel);
        MainMenu mainMenu = new MainMenu(ui, soundPlayer);
        GamePanel gamePanel = new GamePanel(ui, soundPlayer);


        cardPanel.add(mainMenu);

        cardPanel.add(gamePanel, "GamePanel");

        //switches between the menu and game panel
        mainMenu.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ui.isPlayable()) {
                    cardLayout.show(cardPanel, "GamePanel");
                    gamePanel.requestFocusInWindow();
                    mainMenu.stopThread();
                    gamePanel.startGameThread();
                }
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
        setVisible(true);
        mainMenu.startMainMenuThread();

    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
