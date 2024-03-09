package main;

import entity.Entity;

import java.awt.*;

public class Collisions {

    //collisions with the screen borders
    public static boolean isAtRightScreenBorder(Entity entity, float speed) {

        return entity.getX() + entity.getHITBOX_OFFSET() + entity.getHitbox().width + speed >
                Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static boolean isAtLeftScreenBorder(Entity entity, float speed) {

        return entity.getX() + entity.getHITBOX_OFFSET() + speed < 0;
    }
}
