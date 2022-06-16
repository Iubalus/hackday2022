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
        drawTriangle(graphics, params.getBase(), params.getWidth() / 2, 0);
        final int pointPositionY = (int) (Math.sin(Math.PI / 3.0) * params.base);
        graphics.setColor(params.getColor());

        drawInvertedTriangle(
                graphics,
                params.getBase() / 2,
                params.getWidth() / 2,
                pointPositionY,
                params.getDepth()
        );
//        thatKindaCoolTho(graphics, params.getBase()/2, params.getWidth()/2, pointPositionY, 15);


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

    private void drawInvertedTriangle(Graphics2D graphics, int base, int x, int y, int depth) {
        if (depth == 0) {
            return;
        }
        int o = (int) (Math.sin(Math.PI / 3.0) * base);
        int a = base / 2;

        graphics.drawLine(x, y, x - a, y - o);
        graphics.drawLine(x, y, x + a, y - o);
        graphics.drawLine(x - a, y - o, x + a, y - o);
        drawInvertedTriangle(graphics, base / 2, x - (base / 2), y, depth - 1);
        drawInvertedTriangle(graphics, base / 2, x + (base / 2), y, depth - 1);
        drawInvertedTriangle(graphics, base / 2, x, y - o, depth - 1);
    }

    public static class R implements ImageSettings {
        private final ImageSettings baseSettings;
        private final Color color;
        private final int depth;
        private final int base;

        public R(ImageSettings baseSettings, Color color, int depth, int base) {
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

        public int getBase() {
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
                    Integer.parseInt(rawParameters.get("base"))
            );
        }
    }
}
