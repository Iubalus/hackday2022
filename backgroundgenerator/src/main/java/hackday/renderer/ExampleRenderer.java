package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class ExampleRenderer implements ImageRenderer<ExampleRenderer.R> {
    public BufferedImage render(R params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        ImageRendererUtils.renderBackground(params, graphics);
        graphics.setColor(Color.RED);
        graphics.drawLine(0,0,params.getWidth(),params.getHeight());
        return image;
    }


    public static class R implements ImageSettings {
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
        ImageRendererUtils.saveImage(
                new ExampleRenderer()
                        .render(new R(
                                100,
                                100,
                                Color.BLUE
                        )),
                "image"
        );
    }

    public static class Factory implements RendererFactory<ExampleRenderer, R> {

        public ExampleRenderer create() {
            return new ExampleRenderer();
        }

        public R setting(ImageSettings base, Map<String, String> rawParameters) {
            return new R(base.getWidth(), base.getHeight(), base.getBackground());
        }
    }
}
