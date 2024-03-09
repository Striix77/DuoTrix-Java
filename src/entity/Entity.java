package entity;

import main.*;
import projectiles.ProjectileController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {

    //class for each entity
    private float x, y;
    private float speedX, speedY;
    private float jumpingSpeed;
    private float gravity;
    private float MAX_SPEED = 10;
    private boolean transforming = false;

    private boolean jumped = true;
    private boolean leftOrRight = true;
    private boolean enabled;

    BufferedImage jumpLeft, jumpRight;

    ArrayList<BufferedImage> right;
    ArrayList<BufferedImage> left;
    ArrayList<BufferedImage> meleeLeft;
    ArrayList<BufferedImage> meleeRight;
    private String direction;

    //walking sprites
    private int spriteCounter = 0;
    private int spriteNumber = 1;

    //melee sprites
    private int meleeSpriteCounter = 0;
    private int meleeSpriteNumber = 1;

    //hitbox
    private Rectangle hitbox;
    final private float HITBOX_OFFSET = 30;

    public GamePanel gp;
    public KeyHandler keyH;
    public SoundPlayer soundPlayer;
    private boolean isPlayingMeleeSound = false;

    private boolean invincibility = false;

    private ProjectileController pc;


    public void initHitbox(int width, int height) {
        hitbox = new Rectangle();
        hitbox.width = width / 2;
        hitbox.height = height;
        hitbox.x = (int) (x + HITBOX_OFFSET);
        hitbox.y = (int) y;
    }

    //update hitbox location
    public void updateHitbox() {
        hitbox.x = (int) (x + HITBOX_OFFSET);
        hitbox.y = (int) y;
    }

    public void drawHitbox(Graphics2D g2) {
        g2.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    //sets the positions of the player
    public void setPosition(float x, float y, String direction, float speedX, float speedY) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speedX = speedX;
        this.speedY = speedY;
    }


    //get the hitbox of the melee attack
    public Rectangle getMeleeHitbox() {

        if (Objects.equals(direction, "right") || Objects.equals(direction, "jumpRight") || Objects.equals(direction, "meleeRight")) {
            return new Rectangle(hitbox.x + hitbox.width / 2, hitbox.y, 40, GamePanel.tileSize);
        }
        return new Rectangle(hitbox.x + hitbox.width / 2 - 50, hitbox.y, 40, GamePanel.tileSize);
    }

    public boolean checkIfInHitbox(Player other) {

        return HelpingMath.areOverlapping(getMeleeHitbox(), other.getEnabledEntity().getHitbox());
    }

    public boolean checkIfProjectileInHitbox(Rectangle projectileHitbox, Player other) {
        return HelpingMath.areOverlapping(projectileHitbox, other.getEnabledEntity().getHitbox());
    }


    //getters, setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getMAX_SPEED() {
        return MAX_SPEED;
    }

    public void setMAX_SPEED(float MAX_SPEED) {
        this.MAX_SPEED = MAX_SPEED;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public void setSpriteNumber(int spriteNumber) {
        this.spriteNumber = spriteNumber;
    }

    public int getMeleeSpriteCounter() {
        return meleeSpriteCounter;
    }

    public void setMeleeSpriteCounter(int meleeSpriteCounter) {
        this.meleeSpriteCounter = meleeSpriteCounter;
    }

    public int getMeleeSpriteNumber() {
        return meleeSpriteNumber;
    }

    public void setMeleeSpriteNumber(int meleeSpriteNumber) {
        this.meleeSpriteNumber = meleeSpriteNumber;
    }

    public float getHITBOX_OFFSET() {
        return HITBOX_OFFSET;
    }

    public boolean isTransforming() {
        return !transforming;
    }

    public void setTransforming(boolean transforming) {
        this.transforming = transforming;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public void setPlayingMeleeSound(boolean playingMeleeSound) {
        isPlayingMeleeSound = playingMeleeSound;
    }

    public boolean isLeftOrRight() {
        return leftOrRight;
    }

    public void setLeftOrRight(boolean leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInvincibility() {
        return !invincibility;
    }

    public void setInvincibility(boolean invincibility) {
        this.invincibility = invincibility;
    }

    public void setJumpingSpeed(float jumpingSpeed) {
        this.jumpingSpeed = jumpingSpeed;
    }

    public ProjectileController getProjectileController() {
        return pc;
    }

    public void setProjectileController(ProjectileController pc) {
        this.pc = pc;
    }

    //movement of the player
    public void updateMovement() {

        //constantly adds a variable to the position
        if (!Collisions.isAtRightScreenBorder(this, getSpeedX()) && !Collisions.isAtLeftScreenBorder(this, getSpeedX())) {
            setX(getX() + getSpeedX());
        } else {
            if (Collisions.isAtRightScreenBorder(this, getSpeedX())) {
                setX(getX() + getSpeedX() - (getX() + getHITBOX_OFFSET() + getHitbox().width + getSpeedX() - gp.getWidth()));
            }
            if (Collisions.isAtLeftScreenBorder(this, getSpeedX())) {
                setX(-getHITBOX_OFFSET());
            }
        }
        setY(getY() + getSpeedY());

        if (keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed && !jumped) {
                soundPlayer.playJumpSound();
                jumped = true;
                setSpeedY(jumpingSpeed);
                if (Objects.equals(getDirection(), "right")) {
                    setDirection("jumpRight");
                } else if (Objects.equals(getDirection(), "left")) {
                    setDirection("jumpLeft");
                }
            }

            if (keyH.leftPressed) {

                setSpeedX(-4);
                if (!jumped) {
                    setDirection("left");
                } else {
                    setDirection("jumpLeft");
                }
                leftOrRight = false;
            }
            if (keyH.rightPressed) {

                setSpeedX(4);
                if (!jumped) {
                    setDirection("right");
                } else {
                    setDirection("jumpRight");
                }
                leftOrRight = true;
            }
        }
        if (!keyH.rightPressed && !keyH.leftPressed) {
            setSpeedX(0);
        }
        if (keyH.meleeAttack) {
            //melee sound
            if (!isPlayingMeleeSound) {
                isPlayingMeleeSound = true;
                soundPlayer.playMeleeSound();
            }
            //melee animation
            if (Objects.equals(getDirection(), "right") || Objects.equals(getDirection(), "jumpRight")) {
                setDirection("meleeRight");
            }
            if (Objects.equals(getDirection(), "left") || Objects.equals(getDirection(), "jumpLeft")) {
                setDirection("meleeLeft");
            }
        } else {
            isPlayingMeleeSound = false;
        }


    }


}
