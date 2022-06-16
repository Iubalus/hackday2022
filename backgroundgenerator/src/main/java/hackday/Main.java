package hackday;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        image.setRGB(10, 10, Color.RED.getRGB());
        ImageIO.write(image, "png", new File("./image.png"));
    }
}
