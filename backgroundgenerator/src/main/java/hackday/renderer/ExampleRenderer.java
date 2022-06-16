package hackday.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExampleRenderer implements ImageRenderer<ExampleRenderer.R>{
    public BufferedImage render(R params) {
        BufferedImage image = new BufferedImage(params.getWidth(), params.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(params.getBackground());
        graphics.fillRect(0,0, params.getWidth(), params.getHeight());
        graphics.setColor(Color.RED);
        graphics.drawLine(0,0,params.getWidth(),params.getHeight());
        return image;
    }


    public static class R implements ImageSettings{
        private final int width;
        private final int height;
        private final Color background;

        public R(int width, int height, Color background) {
            this.width = width;
            this.height = height;
            this.background = background;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Color getBackground() {
            return background;
        }
    }
    public static void main(String[] args) throws IOException {

        ImageIO.write(new ExampleRenderer().render(new R(100,100,Color.BLUE)), "png", new File("./image.png"));
    }
}
