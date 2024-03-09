package projectiles;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OmnicBomb {

    private BufferedImage bombImage;
    private int dir = 1;
    private int speedX;
    private float speedY;
    private float gravity = 0.15f;
    private float x, y;
    private Rectangle hitbox;

    public OmnicBomb(BufferedImage bombImage, int dir, int speed, float x, float y) {
        this.bombImage = bombImage;
        this.dir = dir;
        this.speedX = speed;
        this.x = x;
        this.y = y;
        speedY = 1;
        hitbox = new Rectangle((int) x + GamePanel.tileSize / 2 - 7, (int) y + GamePanel.tileSize / 2 - 7, GamePanel.tileSize / 10, GamePanel.tileSize / 7);
    }

    //updates the position
    public void updatePosition() {
        hitbox.x += speedX * dir;
        hitbox.y += (int) speedY;
        x += speedX * dir;
        y += (int) speedY;
        speedY += speedY * gravity;
    }

    //draws the bomb
    public void drawBomb(Graphics2D g2) {
        g2.drawImage(bombImage, (int) x, (int) y, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    //getters, setters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public Rectangle getBombHitbox() {
        return hitbox;
    }
}
