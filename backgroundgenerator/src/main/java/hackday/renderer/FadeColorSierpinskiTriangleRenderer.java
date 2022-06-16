package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

public class FadeColorSierpinskiTriangleRenderer implements ImageRenderer<FadeColorSierpinskiTriangleRenderer.R> {

    public BufferedImage render(FadeColorSierpinskiTriangleRenderer.R params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(params.getBackground());
        graphics.fillRect(0, 0, params.getWidth(), params.getHeight());

        Color[] colors = new Color[]{Color.WHITE, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.BLACK};
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
//            int yoff = 1000 * i;
//            int xoff = (700 * i) - 1000;
            int yoff = random.nextInt(1500) - random.nextInt(1500);
            int xoff = random.nextInt(1500) - random.nextInt(1500);
            graphics.setColor(colors[i % colors.length]);
            drawTriangle(graphics, params.getBase(), (params.getWidth() / 2) + xoff, yoff);
            drawInvertedTriangle(
                    graphics,
                    params.getBase() / 2,
                    (params.getWidth() / 2) + xoff,
                    (int) (Math.sin(Math.PI / 3.0) * params.getBase()) + yoff,
                    params.getDepth(),
                    params.getDistort(),
                    colors[i % colors.length]
            );
        }


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


    private void drawInvertedTriangle(
            Graphics2D graphics,
            int base,
            int x,
            int y,
            int depth,
            int baseDistortion,
            Color c
    ) {
        if (depth <= 0 || base == 0) {
            return;
        }
        int o = (int) (Math.sin(Math.PI / 3.0) * base);
        int a = base / 2;
        int distort = baseDistortion + ((15 - depth) * baseDistortion);
        int colorDegrade = 30;
        Random random = new Random();
        graphics.setColor(degradeColor(c, colorDegrade, random));
        graphics.drawLine(
                x + random.nextInt(distort) - random.nextInt(distort),
                y,
                x - a + random.nextInt(distort) - random.nextInt(distort),
                y - o
        );
        graphics.setColor(degradeColor(c, colorDegrade, random));
        graphics.drawLine(
                x + random.nextInt(distort) - random.nextInt(distort),
                y,
                x + a + random.nextInt(distort) - random.nextInt(distort),
                y - o
        );
        graphics.setColor(degradeColor(c, colorDegrade, random));
        graphics.drawLine(
                x - a + random.nextInt(distort) - random.nextInt(distort),
                y - o,
                x + a + random.nextInt(distort) - random.nextInt(distort),
                y - o
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x - (base / 2) + random.nextInt(distort) - random.nextInt(distort),
                y,
                depth - 1, baseDistortion,
                degradeColor(c, colorDegrade, random)
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x + (base / 2) + random.nextInt(distort) - random.nextInt(distort),
                y,
                depth - 1, baseDistortion,
                degradeColor(c, colorDegrade, random)
        );
        drawInvertedTriangle(
                graphics,
                base / 2,
                x + random.nextInt(distort) - random.nextInt(distort),
                y - o,
                depth - 1,
                baseDistortion,
                degradeColor(c, colorDegrade, random)
        );
    }

    private Color degradeColor(Color c, int colorDegrade, Random random) {
//        return new Color(random.nextInt());

        int r = c.getRed() - random.nextInt(colorDegrade);
        int g = c.getGreen() - random.nextInt(colorDegrade);
        int b = c.getBlue() - random.nextInt(colorDegrade);
        return new Color(
                Math.min(Math.max(r, 0), 255),
                Math.min(Math.max(g, 0), 255),
                Math.min(Math.max(b, 0), 255)
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

    public static class Factory implements RendererFactory<FadeColorSierpinskiTriangleRenderer, R> {
        public FadeColorSierpinskiTriangleRenderer create() {
            return new FadeColorSierpinskiTriangleRenderer();
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
