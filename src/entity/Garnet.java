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


public class Garnet extends Entity {

    public Level lb;
    private long lastPressTime = 0;
    final private long pressInterval = 1000;


    public Garnet(GamePanel gp, KeyHandler keyH, ProjectileController pc, SoundPlayer soundPlayer) {
        this.gp = gp;
        this.keyH = keyH;
        initHitbox(GamePanel.tileSize, GamePanel.tileSize);
        setDefaultValues();
        getPlayerImage();
        setProjectileController(pc);
        this.soundPlayer = soundPlayer;
    }

    public void setDefaultValues() {
        setX(100);
        setY(100);
        setSpeedX(0);
        setSpeedY(4);
        setJumpingSpeed(-16);
        setDirection("right");
        lb = LevelManager.getActiveLevel();
        setGravity(0.4f);
        setEnabled(false);
        right = new ArrayList<>();
        left = new ArrayList<>();
        meleeRight = new ArrayList<>();
        meleeLeft = new ArrayList<>();
    }

    public void getPlayerImage() {
        try {

            //loads the images for Garnet
            for (int i = 1; i <= 4; i++) {
                right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetAnimRight" + i + ".png"))));

            }
            for (int i = 1; i <= 4; i++) {
                left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetAnimLeft" + i + ".png"))));

            }


            jumpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetAnimLeftJump1.png")));
            jumpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetAnimRightJump1.png")));


            for (int i = 1; i <= 6; i++) {
                meleeRight.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetMeleeRight" + i + ".png"))));

            }
            for (int i = 1; i <= 6; i++) {
                meleeLeft.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HeroGarnetMeleeLeft" + i + ".png"))));

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
            if (getSpriteCounter() > 11) {
                if (getSpriteNumber() == 1) {
                    setSpriteNumber(2);
                } else if (getSpriteNumber() == 2) {
                    setSpriteNumber(3);
                } else if (getSpriteNumber() == 3) {
                    setSpriteNumber(4);

                } else if (getSpriteNumber() == 4) {
                    setSpriteNumber(1);
                }
                setSpriteCounter(0);
            }
        } else {
            setSpriteNumber(1);
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


        //falling and jumping
        if (!lb.isOnGround(getY() + getSpeedY(), GamePanel.tileSize)) {
            setSpeedY(getSpeedY() + getGravity());

            if (getSpeedY() > getMAX_SPEED()) {
                setSpeedY(getMAX_SPEED());
            }
        } else {
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

        //shooting

        if (keyH.shooting && !keyH.meleeAttack) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPressTime >= pressInterval) {
                lastPressTime = currentTime;
                if (Objects.equals(getDirection(), "right") || Objects.equals(getDirection(), "jumpRight")) {
                    getProjectileController().addGlove(1, 20, getX() + getHITBOX_OFFSET(), getY());
                } else {
                    getProjectileController().addGlove(-1, 20, getX() + getHITBOX_OFFSET(), getY());
                }

            }
            keyH.shooting = false;


        }


    }

    public void draw(Graphics2D g2) {

        //draws Finn
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
