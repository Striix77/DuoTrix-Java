package projectiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ProjectileSprites {
    public static BufferedImage omnicBombRight;
    public static BufferedImage omnicBombLeft;

    //loads the images of the projectiles
    static {
        try {
            omnicBombRight = ImageIO.read(Objects.requireNonNull(ProjectileSprites.class.getResourceAsStream("/OmnicBombRight.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            omnicBombLeft = ImageIO.read(Objects.requireNonNull(ProjectileSprites.class.getResourceAsStream("/OmnicBombLeft.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage garnetGlove;

    static {
        try {
            garnetGlove = ImageIO.read(Objects.requireNonNull(ProjectileSprites.class.getResourceAsStream("/GarnetGlove.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
