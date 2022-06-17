package hackday;

import hackday.parse.InputParser;
import hackday.parse.Parsed;
import hackday.renderer.DistortedSierpinskiTriangleRenderer;
import hackday.renderer.ExampleRenderer;
import hackday.renderer.FadeColorSierpinskiTriangleRenderer;
import hackday.renderer.FadingSierpinskiTriangleRenderer;
import hackday.renderer.PrettyCoolTho;
import hackday.renderer.RotatingSquareRenderer;
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
        parser.register("fade", new FadingSierpinskiTriangleRenderer.Factory());
        parser.register("fade_color", new FadeColorSierpinskiTriangleRenderer.Factory());
        parser.register("rot_square", new RotatingSquareRenderer.Factory());
        Parsed parse = parser.parse(
                "width:3840;height:2160;background:#FFFFFF;rot_square(color:#0000FF,size:1000,x:1420,y:580)");
        BufferedImage image = parse.render();
        ImageIO.write(image, "png", new File("./examples/" + System.currentTimeMillis() + ".png"));
    }
}
