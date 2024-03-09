package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CharacterSwitcher implements KeyListener {
    //switches between the 3 characters on button presses and animates the transformaton flash
    public int switchCharacters = -1;
    public boolean switched = false;
    public boolean switching = false;
    private BufferedImage[] transformImages;
    private int transCounter = 0;
    private int transNumber = 1;
    private GamePanel gp;
    private float flashX, flashY;
    private int player;


    public CharacterSwitcher(GamePanel gp, int player) {
        transformImages = new BufferedImage[11];
        this.player = player;
        this.gp = gp;
        try {
            for (int i = 0; i < 11; i++) {
                transformImages[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/TransformationFlash" + (i + 1) + ".png")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!switching) {
            if (player == 2) {
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    switchCharacters = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    switchCharacters = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    switchCharacters = 2;
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    switchCharacters = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    switchCharacters = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    switchCharacters = 2;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateTransformationFlash(float x, float y) {
        flashX = x;
        flashY = y;
        //switches between pictures
        transCounter++;
        if (transCounter > 2) {
            if (transNumber == 1) {
                transNumber = 2;
            } else if (transNumber == 2) {
                transNumber = 3;
            } else if (transNumber == 3) {
                transNumber = 4;
            } else if (transNumber == 4) {
                transNumber = 5;
            } else if (transNumber == 5) {
                transNumber = 6;
            } else if (transNumber == 6) {
                transNumber = 7;
            } else if (transNumber == 7) {
                transNumber = 8;
            } else if (transNumber == 8) {
                transNumber = 9;
            } else if (transNumber == 9) {
                transNumber = 10;
            } else if (transNumber == 10) {
                transNumber = 11;
            } else if (transNumber == 11) {
                transNumber = 1;
                switched = true;
                switching = false;
            }

            transCounter = 0;
        }

    }

    public void drawFlash(Graphics2D g2) {
        //draws the transforming animation
        BufferedImage flashImage;

        flashImage = transformImages[transNumber - 1];

        g2.drawImage(flashImage, (int) flashX, (int) flashY, null);
    }
}
