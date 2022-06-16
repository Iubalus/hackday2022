package hackday;

import hackday.renderer.DistortedSierpinskiTriangleRenderer;
import hackday.renderer.ExampleRenderer;
import hackday.parse.InputParser;
import hackday.parse.Parsed;
import hackday.renderer.PrettyCoolTho;
import hackday.renderer.SierpinskiTriangleRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        InputParser parser = new InputParser();
        parser.register("example", new ExampleRenderer.Factory());
        parser.register("sierpinski", new SierpinskiTriangleRenderer.Factory());
        parser.register("disierpinski", new DistortedSierpinskiTriangleRenderer.Factory());
        parser.register("cool_tho", new PrettyCoolTho.Factory());

        Parsed parse = parser.parse(
                "width:10000;height:10000;background:#000000;sierpinski(color:#32F7FA,depth:15,base:7500)");
        BufferedImage image = parse.render();
        ImageIO.write(image, "png", new File("./examples/" + System.currentTimeMillis() + ".png"));
    }
}
