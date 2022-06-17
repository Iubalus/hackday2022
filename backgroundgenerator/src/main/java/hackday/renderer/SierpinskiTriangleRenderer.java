package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class SierpinskiTriangleRenderer implements ImageRenderer<SierpinskiTriangleRenderer.R> {

    public BufferedImage render(SierpinskiTriangleRenderer.R params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(params.getBackground());
        graphics.fillRect(0, 0, params.getWidth(), params.getHeight());

        graphics.setColor(params.getColor());
        drawTriangle(graphics, params.getBase(), params.getWidth() / 2.0, 0);
        final int pointPositionY = (int) (Math.sin(Math.PI / 3.0) * params.base);
        graphics.setColor(params.getColor());

        drawInvertedTriangle(
                graphics,
                params.getBase() / 2,
                params.getWidth() / 2.0,
                pointPositionY,
                params.getDepth()
        );
//        thatKindaCoolTho(graphics, params.getBase()/2, params.getWidth()/2, pointPositionY, 15);


        return image;
    }

    private void drawTriangle(Graphics2D graphics, double base, double x, double y) {
        double opposite = (int) (Math.sin(Math.PI / 3.0) * base);
        double adjacent = base / 2;
        graphics.drawLine((int) x, (int) y, (int) (x - adjacent), (int) (y + opposite));
        graphics.drawLine((int) x, (int) y, (int) (x + adjacent), (int) (y + opposite));
        graphics.drawLine(
                (int) (x - adjacent),
                (int) (y + opposite),
                (int) (x + adjacent),
                (int) (y + opposite)
        );
    }

    private void drawInvertedTriangle(Graphics2D graphics, double base, double x, double y, int depth) {
        if (depth == 0) {
            return;
        }
        double o = (Math.sin(Math.PI / 3.0) * base);
        double a = base / 2;

        graphics.drawLine((int) x, (int) y, (int) (x - a), (int) (y - o));
        graphics.drawLine((int) x, (int) y, (int) (x + a), (int) (y - o));
        graphics.drawLine((int) (x - a), (int) (y - o), (int) (x + a), (int) (y - o));
        drawInvertedTriangle(graphics, base / 2, x - (base / 2), y, depth - 1);
        drawInvertedTriangle(graphics, base / 2, x + (base / 2), y, depth - 1);
        drawInvertedTriangle(graphics, base / 2, x, y - o, depth - 1);
    }

    public static class R implements ImageSettings {
        private final ImageSettings baseSettings;
        private final Color color;
        private final int depth;
        private final double base;

        public R(ImageSettings baseSettings, Color color, int depth, double base) {
            this.baseSettings = baseSettings;
            this.color = color;
            this.depth = depth;
            this.base = base;
        }

        public int getWidth() {
            return baseSettings.getWidth();
        }

        public int getHeight() {
            return baseSettings.getHeight();
        }

        public Color getBackground() {
            return baseSettings.getBackground();
        }

        public Color getColor() {
            return color;
        }

        public int getDepth() {
            return depth;
        }

        public double getBase() {
            return base;
        }
    }

    public static class Factory implements RendererFactory<SierpinskiTriangleRenderer, R> {
        public SierpinskiTriangleRenderer create() {
            return new SierpinskiTriangleRenderer();
        }

        public R setting(
                ImageSettings base, Map<String, String> rawParameters
        ) {
            return new R(
                    base,
                    Color.decode(rawParameters.get("color")),
                    Integer.parseInt(rawParameters.get("depth")),
                    Double.parseDouble(rawParameters.get("base"))
            );
        }
    }
}
