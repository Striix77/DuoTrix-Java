package entity;

import levels.Level;
import levels.LevelManager;
import main.GamePanel;
import main.KeyHandler;
import main.SoundPlayer;
import projectiles.ProjectileController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Omnic extends Entity {

    public Level lb;
    private long lastPressTime = 0;
    final private long pressInterval = 750;


    public Omnic(GamePanel gp, KeyHandler keyH, ProjectileController pc, SoundPlayer soundPlayer) {
        this.gp = gp;
        this.keyH = keyH;
        initHitbox(GamePanel.tileSize, GamePanel.tileSize);
        setDefaultValues();
        getOmnicImage();
        setProjectileController(pc);
        this.soundPlayer = soundPlayer;
    }

    public void setDefaultValues() {
        setX(100);
        setY(100);
        setSpeedX(0);
        setSpeedY(4);
        setJumpingSpeed(-8);
        setDirection("right");
        lb = LevelManager.getActiveLevel();
        setGravity(0.6f);
        setEnabled(false);
        setMAX_SPEED(15);
        right = new ArrayList<>();
        left = new ArrayList<>();
        meleeRight = new ArrayList<>();
        meleeLeft = new ArrayList<>();
    }

    public void getOmnicImage() {
        try {
            //loads the images for Garnet
            for (int i = 1; i <= 7; i++) {
                right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BomberOmnicAnimRight" + i + ".png"))));

            }
            for (int i = 1; i <= 7; i++) {
                left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BomberOmnicAnimLeft" + i + ".png"))));

            }


            jumpLeft = left.get(0);
            jumpRight = right.get(0);


            for (int i = 1; i <= 6; i++) {
                meleeRight.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BomberOmnicMeleeRight" + i + ".png"))));

            }
            for (int i = 1; i <= 6; i++) {
                meleeLeft.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BomberOmnicMeleeLeft" + i + ".png"))));

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {

        //updates the positions of the player
        updateMovement();
        if (keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {

            setSpriteCounter(getSpriteCounter() + 1);
            if (getSpriteCounter() > 7) {
                if (getSpriteNumber() == 1) {
                    setSpriteNumber(2);
                } else if (getSpriteNumber() == 2) {
                    setSpriteNumber(3);
                } else if (getSpriteNumber() == 3) {
                    setSpriteNumber(4);

                } else if (getSpriteNumber() == 4) {
                    setSpriteNumber(5);
                } else if (getSpriteNumber() == 5) {
                    setSpriteNumber(6);
                } else if (getSpriteNumber() == 6) {
                    setSpriteNumber(7);
                } else if (getSpriteNumber() == 7) {
                    setSpriteNumber(1);
                }
                setSpriteCounter(0);
            }
        } else {
            setSpriteNumber(2);
        }

        if (keyH.meleeAttack) {

            setMeleeSpriteCounter(getMeleeSpriteCounter() + 1);
            if (getMeleeSpriteCounter() > 2) {
                if (getMeleeSpriteNumber() == 1) {
                    setMeleeSpriteNumber(2);
                } else if (getMeleeSpriteNumber() == 2) {
                    setMeleeSpriteNumber(3);
                } else if (getMeleeSpriteNumber() == 3) {
                    setMeleeSpriteNumber(4);
                } else if (getMeleeSpriteNumber() == 4) {
                    setMeleeSpriteNumber(5);
                } else if (getMeleeSpriteNumber() == 5) {
                    setMeleeSpriteNumber(6);
                } else if (getMeleeSpriteNumber() == 6) {
                    setMeleeSpriteNumber(1);
                    keyH.meleeAttack = false;
                    setPlayingMeleeSound(false);
                }
                setMeleeSpriteCounter(0);
            }
        } else {
            setMeleeSpriteNumber(1);
        }

        //shooting

        if (keyH.shooting && !keyH.meleeAttack) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPressTime >= pressInterval) {
                lastPressTime = currentTime;
                if (Objects.equals(getDirection(), "right") || Objects.equals(getDirection(), "jumpRight") || Objects.equals(getDirection(), "meleeRight")) {
                    getProjectileController().addBomb(1, 20, getX() + getHITBOX_OFFSET(), getY());
                } else {
                    getProjectileController().addBomb(-1, 20, getX() + getHITBOX_OFFSET(), getY());
                }

            }
            keyH.shooting = false;

        }

        //falling
        if (!lb.isOnGround(getY() + getSpeedY(), GamePanel.tileSize)) {
            setJumped(true);
            setSpeedY(getSpeedY() + getGravity());
            if (getSpeedY() > 0 && !keyH.downPressed) {
                if (getGravity() >= 0.6f) {
                    setGravity(getGravity() / 10);


                }
            } else {
                setGravity(0.6f);
            }

            if (getSpeedY() > getMAX_SPEED()) {
                setSpeedY(getMAX_SPEED());
            }
        } else {
            setGravity(0.6f);
            setJumped(false);
            setSpeedY(0);
            setY(685 - GamePanel.tileSize);
            if (!keyH.meleeAttack) {
                if (isLeftOrRight()) {
                    setDirection("right");
                } else {
                    setDirection("left");
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        //draws Omnic
        updateHitbox();
        BufferedImage image = null;
        if (isTransforming()) {

            image = switch (getDirection()) {
                case "right" -> right.get(getSpriteNumber() - 1);
                case "left" -> left.get(getSpriteNumber() - 1);
                case "jumpLeft" -> jumpLeft;
                case "jumpRight" -> jumpRight;
                case "meleeLeft" -> meleeLeft.get(getMeleeSpriteNumber() - 1);
                case "meleeRight" -> meleeRight.get(getMeleeSpriteNumber() - 1);
                default -> image;
            };

            g2.drawImage(image, (int) getX(), (int) getY(), GamePanel.tileSize, GamePanel.tileSize, null);
        }


    }


}

