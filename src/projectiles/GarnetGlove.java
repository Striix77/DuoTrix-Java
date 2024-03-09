package projectiles;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GarnetGlove {

    //Garnet's projectile
    private BufferedImage gloveImage;
    private int dir = 1;
    private int speedX;
    private float speedY;
    private float gravity = 0.15f;
    private float x, y;
    private Rectangle hitbox;

    public GarnetGlove(BufferedImage gloveImage, int dir, int speed, float x, float y) {
        this.gloveImage = gloveImage;
        this.dir = dir;
        this.speedX = speed;
        this.x = x;
        this.y = y;
        speedY = 1;
        hitbox = new Rectangle((int) x + GamePanel.tileSize / 2 - 7, (int) y + GamePanel.tileSize / 2 - 7, GamePanel.tileSize / 10, GamePanel.tileSize / 7);
    }

    //updates the positions of the glove
    public void updatePosition() {
        hitbox.x += speedX * dir;
        x += speedX * dir;
    }

    //draws the glove
    public void drawGlove(Graphics2D g2) {
        g2.drawImage(gloveImage, (int) x, (int) y, GamePanel.tileSize, GamePanel.tileSize, null);
    }


    //getters,setters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getGloveHitbox() {
        return hitbox;
    }
}
