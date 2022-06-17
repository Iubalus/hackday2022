package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

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
        double[] xs = {params.getX(), params.getX() + params.getSize(), params.getX() + params.getSize(), params.getX(), params.getX()};
        double[] ys = {params.getY(), params.getY(), params.getY() + params.getSize(), params.getY() + params.getSize(), params.getY()};

        drawPolygon(graphics, xs, ys);

        //Configurables
        final int cycles = 1500;
        double angle = Math.PI / 100;

        final Random r = new Random();
        for (int j = 0; j < cycles; j++) {
            for (int i = 0; i < xs.length; i++) {
                xs[i] = rotateX(params.getX(), params.getY(), params.getSize(), xs[i], ys[i], angle);
                ys[i] = rotateY(params.getX(), params.getY(), params.getSize(), xs[i], ys[i], angle);
            }
            graphics.setColor(new Color(
                    100 + r.nextInt(50),
                    100 + r.nextInt(125),
                    100 + r.nextInt(125)
            ));
            drawPolygon(graphics, xs, ys);
        }
        return image;
    }

    private double rotateX(double xPos, double yPos, double size, double x, double y, double angleRadians) {
        return (((x - size / 2) - xPos) * Math.cos(angleRadians) - ((y - size / 2) - yPos) * Math.sin(angleRadians)) + xPos + size / 2;
    }

    private double rotateY(double xPos, double yPos, double size, double x, double y, double angleRadians) {
        return (((x - size / 2) - xPos) * Math.sin(angleRadians) + ((y - size / 2) - yPos) * Math.cos(angleRadians)) + yPos + size / 2;
    }

    private void drawPolygon(Graphics2D graphics, double[] xs, double[] ys) {
        graphics.drawPolygon(toIntArray(xs), toIntArray(ys), xs.length);
    }

    private int[] toIntArray(double[] source) {
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i] = (int) source[i];
        }
        return target;
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
