package hackday.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRendererUtils {

    public static void renderBackground(ImageSettings settings, Graphics2D graphics) {
        graphics.setColor(settings.getBackground());
        graphics.fillRect(0, 0, settings.getWidth(), settings.getHeight());
    }

    public static void saveImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(
                image,
                "png",
                new File(String.format("./%s.png", fileName))
        );
    }
}
