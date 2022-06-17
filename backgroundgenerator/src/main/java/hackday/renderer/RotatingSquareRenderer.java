package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class RotatingSquareRenderer implements ImageRenderer<RotatingSquareRenderer.R> {
    public BufferedImage render(R params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        ImageRendererUtils.renderBackground(params, graphics);

        graphics.setColor(params.getColor());
        int[] xs = {params.getX(), params.getX() + params.getSize(), params.getX() + params.getSize(), params.getX(), params.getX()};
        int[] ys = {params.getY(), params.getY(), params.getY() + params.getSize(), params.getY() + params.getSize(), params.getY()};
        graphics.drawPolygon(xs, ys, 5);
        return image;
    }


    public static class R implements ImageSettings {
        private final int width;
        private final int height;
        private final Color background;
        private final Color color;
        private final int x;
        private final int y;
        private final int size;

        public R(int width, int height, Color background, Color color, int x, int y, int size) {
            this.width = width;
            this.height = height;
            this.background = background;
            this.color = color;
            this.x = x;
            this.y = y;
            this.size = size;
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

        public Color getColor() {
            return color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return size;
        }
    }

    public static class Factory implements RendererFactory<RotatingSquareRenderer, R> {

        public RotatingSquareRenderer create() {
            return new RotatingSquareRenderer();
        }

        public R setting(ImageSettings base, Map<String, String> rawParameters) {
            return new R(
                    base.getWidth(),
                    base.getHeight(),
                    base.getBackground(),
                    Color.decode(rawParameters.get("color")),
                    parseInt(rawParameters.get("x"), 0),
                    parseInt(rawParameters.get("y"), 0),
                    parseInt(rawParameters.get("size"), 300)
            );
        }

        private int parseInt(String raw, int defaultValue) {
            if (raw == null) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }
}
