package levels;

import java.awt.image.BufferedImage;

public class LevelManager {

    private static Level activeLevel;

    public static void setActiveLevel(Level level) {
        activeLevel = level;
    }

    public static Level getActiveLevel() {
        return activeLevel;
    }

    public static BufferedImage getLevelBackground() {
        return activeLevel.getBackgroundImage();
    }
}
