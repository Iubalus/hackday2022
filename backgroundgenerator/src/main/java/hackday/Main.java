package hackday;

import hackday.renderer.ExampleRendererFactory;
import hackday.parse.InputParser;
import hackday.parse.Parsed;
import hackday.renderer.SierpinskiTriangleRendererFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        InputParser parser = new InputParser();
        parser.register("example", new ExampleRendererFactory());
        parser.register("sierpinski", new SierpinskiTriangleRendererFactory());

        Parsed parse = parser.parse(
                "width:1920;height:1080;background:#FFFFFF;sierpinski(color:#FF0000,depth:1,base:1000)");
        BufferedImage image = parse.render();
        ImageIO.write(image, "png", new File("./image.png"));
    }
}
