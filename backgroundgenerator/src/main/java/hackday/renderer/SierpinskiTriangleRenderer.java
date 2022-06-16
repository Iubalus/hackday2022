package hackday.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        System.out.println(pointPositionY);
        graphics.setColor(Color.BLUE);

        drawInvertedTriangle(
                graphics,
                params.getBase() / 2,
                params.getWidth() / 2,
                pointPositionY
        );



        return image;
    }

    private void drawTriangle(Graphics2D graphics, int base, int pointPosX, int pointPositionY) {
        int opposite = (int) (Math.sin(Math.PI / 3.0) * base);
        int adjacent = base / 2;
        graphics.drawLine(pointPosX, pointPositionY, pointPosX - adjacent, pointPositionY + opposite);
        graphics.drawLine(pointPosX, pointPositionY, pointPosX + adjacent, pointPositionY + opposite);
        graphics.drawLine(pointPosX - adjacent, pointPositionY + opposite, pointPosX + adjacent, pointPositionY + opposite);
    }


    private void drawInvertedTriangle(Graphics2D graphics, int base, int pointPosX, int pointPositionY) {
        int opposite = (int) (Math.sin(Math.PI / 3.0) * base);
        int adjacent = base / 2;
        graphics.drawLine(pointPosX, pointPositionY, pointPosX - adjacent, pointPositionY - opposite);
        graphics.drawLine(pointPosX, pointPositionY, pointPosX + adjacent, pointPositionY - opposite);
        graphics.drawLine(pointPosX - adjacent, pointPositionY - opposite, pointPosX + adjacent, pointPositionY - opposite);
    }

    static class NextIteration{
        private final int x;
        private final int y;
        private final int base;

        NextIteration(int x, int y, int base) {
            this.x = x;
            this.y = y;
            this.base = base;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getBase() {
            return base;
        }
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
}
