package levels;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CityLevel implements Level {
    private BufferedImage backgroundImage = null;

    @Override
    public boolean isTouchingSolid(float x, float y, int width, int height) {
        return false;
    }

    @Override
    public boolean isOnGround(float y, int height) {
        return y + height >= groundLevel;
    }

    @Override
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }


    public CityLevel() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/CityBackground.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
