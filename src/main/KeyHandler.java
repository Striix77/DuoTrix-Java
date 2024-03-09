package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    //controlls the players' movements
    public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false,
            meleeAttack = false, playerOne = false, shooting = false, pauseGame = false, pausePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (!pauseGame) {
            if (!playerOne) {
                if (code == KeyEvent.VK_UP) {
                    upPressed = true;
                }
                if (code == KeyEvent.VK_DOWN) {
                    downPressed = true;
                }
                if (code == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                }

                if (code == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }
                if (code == KeyEvent.VK_COMMA) {
                    meleeAttack = true;
                }
                if (code == KeyEvent.VK_PERIOD) {
                    shooting = true;
                }
            } else {
                if (code == KeyEvent.VK_W) {
                    upPressed = true;
                }
                if (code == KeyEvent.VK_S) {
                    downPressed = true;
                }
                if (code == KeyEvent.VK_A) {
                    leftPressed = true;
                }

                if (code == KeyEvent.VK_D) {
                    rightPressed = true;
                }
                if (code == KeyEvent.VK_BACK_QUOTE) {
                    meleeAttack = true;
                }
                if (code == KeyEvent.VK_Q) {
                    shooting = true;
                }
            }
        }

        if (!pausePressed && code == KeyEvent.VK_ESCAPE) {
            pauseGame = !pauseGame;
            pausePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


        if (!playerOne) {
            if (code == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }

            if (code == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }

        } else {
            if (code == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = false;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = false;
            }


        }
        if (code == KeyEvent.VK_ESCAPE) {
            pausePressed = false;
        }
    }

}
