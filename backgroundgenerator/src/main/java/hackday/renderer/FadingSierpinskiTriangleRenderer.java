package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

public class FadingSierpinskiTriangleRenderer implements ImageRenderer<FadingSierpinskiTriangleRenderer.R> {

    public BufferedImage render(FadingSierpinskiTriangleRenderer.R params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(params.getBackground());
        graphics.fillRect(0, 0, params.getWidth(), params.getHeight());

        graphics.setColor(params.getColor());
        drawTriangle(graphics, params.getBase(), params.getWidth() / 2, 0);

        drawInvertedTriangle(
                graphics,
                params.getBase() / 2,
                params.getWidth() / 2,
                (int) (Math.sin(Math.PI / 3.0) * params.getBase()),
                params.getDepth(),
                params.getDistort()
        );

        return image;
    }

    private void drawTriangle(Graphics2D graphics, int base, int pointPosX, int pointPositionY) {
        int opposite = (int) (Math.sin(Math.PI / 3.0) * base);
        int adjacent = base / 2;
        graphics.drawLine(pointPosX, pointPositionY, pointPosX - adjacent, pointPositionY + opposite);
        graphics.drawLine(pointPosX, pointPositionY, pointPosX + adjacent, pointPositionY + opposite);
        graphics.drawLine(
                pointPosX - adjacent,
                pointPositionY + opposite,
                pointPosX + adjacent,
                pointPositionY + opposite
        );
    }


    private void drawInvertedTriangle(Graphics2D graphics, int base, int x, int y, int depth, int baseDistortion) {
        if (depth <= 0 || base == 0) {
            return;
        }
        int o = (int) (Math.sin(Math.PI / 3.0) * base);
        int a = base / 2;
        int distort = baseDistortion + ((15 - depth) * baseDistortion);
        Random random = new Random();
        graphics.drawLine(
                x + random.nextInt(distort),
                y,
                x - a + random.nextInt(distort),
                y - o
        );
        graphics.drawLine(
                x + random.nextInt(distort),
                y,
                x + a + random.nextInt(distort),
                y - o
        );
        graphics.drawLine(
                x - a + random.nextInt(distort),
                y - o,
                x + a + random.nextInt(distort),
                y - o
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x - (base / 2) + random.nextInt(distort),
                y,
                depth - 1, baseDistortion
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x + (base / 2) + random.nextInt(distort),
                y,
                depth - 1, baseDistortion
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x + random.nextInt(distort),
                y - o,
                depth - 1,
                baseDistortion
        );
    }

    public static class R implements ImageSettings {
        private final ImageSettings baseSettings;
        private final Color color;
        private final int depth;
        private final int base;
        private final int distort;

        public R(ImageSettings baseSettings, Color color, int depth, int base, int distort) {
            this.baseSettings = baseSettings;
            this.color = color;
            this.depth = depth;
            this.base = base;
            this.distort = distort;
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

        public int getBase() {
            return base;
        }

        public int getDistort() {
            return distort;
        }
    }

    public static class Factory implements RendererFactory<FadingSierpinskiTriangleRenderer, R> {
        public FadingSierpinskiTriangleRenderer create() {
            return new FadingSierpinskiTriangleRenderer();
        }

        public R setting(
                ImageSettings base, Map<String, String> rawParameters
        ) {
            return new R(
                    base,
                    Color.decode(rawParameters.get("color")),
                    Integer.parseInt(rawParameters.get("depth")),
                    Integer.parseInt(rawParameters.get("base")),
                    Integer.parseInt(rawParameters.get("distort"))
            );
        }
    }
}
