package levels;

import java.awt.image.BufferedImage;

public interface Level {

    int groundLevel = 683;

    boolean isTouchingSolid(float x, float y, int width, int height);

    boolean isOnGround(float y, int height);

    BufferedImage getBackgroundImage();
}
