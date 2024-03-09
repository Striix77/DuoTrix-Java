package main;

import projectiles.GarnetGlove;
import projectiles.OmnicBomb;
import projectiles.ProjectileController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HealthManager {

    //manages the health of the players
    private final GamePanel gamePanel;
    private final PlayerOne playerOne;
    private final PlayerTwo playerTwo;
    private final ProjectileController pc1;
    private final ProjectileController pc2;
    private final Rectangle playerOneHealthBar = new Rectangle();
    private final Rectangle playerTwoHealthBar = new Rectangle();
    private final BufferedImage playerOneHealthBarImage;
    private final BufferedImage playerTwoHealthBarImage;
    private final SoundPlayer soundPlayer;


    public Rectangle getPlayerOneHealthBar() {
        return playerOneHealthBar;
    }

    public Rectangle getPlayerTwoHealthBar() {
        return playerTwoHealthBar;
    }


    public HealthManager(GamePanel gamePanel, PlayerOne playerOne, PlayerTwo playerTwo, ProjectileController pc1, ProjectileController pc2, SoundPlayer soundPlayer) {
        this.gamePanel = gamePanel;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.pc1 = pc1;
        this.pc2 = pc2;
        this.soundPlayer = soundPlayer;
        setHealthBars();
        //loads the health bars of the players
        try {
            playerOneHealthBarImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HealthBar1.png")));
            playerTwoHealthBarImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HealthBar2.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawHealthBars(Graphics2D g2) {

        //draws the health bars
        g2.setColor(new Color(182, 28, 4));
        g2.fill(getPlayerOneHealthBar());
        g2.fill(getPlayerTwoHealthBar());
    }

    public void drawHealthBarImages(Graphics g) {
        g.drawImage(playerOneHealthBarImage, 0, 0, null);
        g.drawImage(playerTwoHealthBarImage, gamePanel.getWidth() - GamePanel.tileSize * 3, 0, null);
    }


    public void setHealthBars() {
        //player one
        getPlayerOneHealthBar().x = 131;
        getPlayerOneHealthBar().y = 47;
        getPlayerOneHealthBar().width = (int) (playerOne.getHealth() / 4.6f);
        getPlayerOneHealthBar().height = 34;


        //player two
        getPlayerTwoHealthBar().x = 1188;
        getPlayerTwoHealthBar().y = 47;
        getPlayerTwoHealthBar().width = (int) (playerTwo.getHealth() / 4.6f);
        getPlayerTwoHealthBar().height = 34;
    }

    public void updateHealthBars() {
        //player one

        getPlayerOneHealthBar().width = (int) (playerOne.getHealth() / 4.6f);

        //player two
        int auxWidth = getPlayerTwoHealthBar().width;
        getPlayerTwoHealthBar().width = (int) (playerTwo.getHealth() / 4.6f);
        getPlayerTwoHealthBar().x = getPlayerTwoHealthBar().x + auxWidth - getPlayerTwoHealthBar().width;
    }

    public void healthUpdate() {
        //finn raises health

        if (playerOne.getEnabledEntity().getClass().getSimpleName().equals("Finn")) {
            playerOne.setHealth(Math.min(playerOne.getHealth() + 0.2f, playerOne.getMaxHealth()));
        }
        if (playerTwo.getEnabledEntity().getClass().getSimpleName().equals("Finn")) {
            playerTwo.setHealth(Math.min(playerTwo.getHealth() + 0.2f, playerTwo.getMaxHealth()));
        }

        //melee attack of the two players
        if (playerOne.getEnabledEntity().keyH.meleeAttack) {

            System.out.println(playerOne.getEnabledEntity().checkIfInHitbox(playerTwo) + " " + playerTwo.getEnabledEntity().getClass().getSimpleName());
            if (playerOne.getEnabledEntity().checkIfInHitbox(playerTwo) && playerTwo.getEnabledEntity().isInvincibility()) {
                playerTwo.setHealth(playerTwo.getHealth() - 25);
                playerTwo.getEnabledEntity().setInvincibility(true);
                playerOne.setScore(playerOne.getScore() + 10);
                soundPlayer.playHurtSound();
            }
        } else {
            playerTwo.getEnabledEntity().setInvincibility(false);
        }


        if (playerTwo.getEnabledEntity().keyH.meleeAttack) {
            if (playerTwo.getEnabledEntity().checkIfInHitbox(playerOne) && playerOne.getEnabledEntity().isInvincibility()) {
                playerOne.setHealth(playerOne.getHealth() - 25);
                playerOne.getEnabledEntity().setInvincibility(true);
                playerTwo.setScore(playerTwo.getScore() + 10);
                soundPlayer.playHurtSound();
            }
        } else {
            playerOne.getEnabledEntity().setInvincibility(false);
        }

        //shooting
        if (!pc1.getBombList().isEmpty()) {
            OmnicBomb tempBomb = pc1.getTempBomb();
            if (playerTwo.getEnabledEntity().checkIfProjectileInHitbox(tempBomb.getBombHitbox(), playerTwo)) {
                playerTwo.setHealth(playerTwo.getHealth() - 50);
                pc1.removeBomb(tempBomb);
                playerOne.setScore(playerOne.getScore() + 50);
                soundPlayer.playHurtSound();
            }
        }
        if (!pc2.getBombList().isEmpty()) {
            OmnicBomb tempBomb = pc2.getTempBomb();
            if (playerOne.getEnabledEntity().checkIfProjectileInHitbox(tempBomb.getBombHitbox(), playerOne)) {
                playerOne.setHealth(playerOne.getHealth() - 50);
                pc2.removeBomb(tempBomb);
                playerTwo.setScore(playerTwo.getScore() + 50);
                soundPlayer.playHurtSound();
            }
        }


        if (!pc1.getGloveList().isEmpty()) {
            GarnetGlove tempGlove = pc1.getTempGlove();
            if (playerTwo.getEnabledEntity().checkIfProjectileInHitbox(tempGlove.getGloveHitbox(), playerTwo)) {
                playerTwo.setHealth(playerTwo.getHealth() - 50);
                pc1.removeGlove(tempGlove);
                playerOne.setScore(playerOne.getScore() + 50);
                soundPlayer.playHurtSound();
            }
        }
        if (!pc2.getGloveList().isEmpty()) {
            GarnetGlove tempGlove = pc2.getTempGlove();
            if (playerOne.getEnabledEntity().checkIfProjectileInHitbox(tempGlove.getGloveHitbox(), playerOne)) {
                playerOne.setHealth(playerOne.getHealth() - 50);
                pc2.removeGlove(tempGlove);
                playerTwo.setScore(playerTwo.getScore() + 50);
                soundPlayer.playHurtSound();
            }
        }
    }
}
