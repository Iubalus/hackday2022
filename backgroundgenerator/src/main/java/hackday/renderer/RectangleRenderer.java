package hackday.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RectangleRenderer implements ImageRenderer<RectangleRenderer.RectangleSettings> {

    @Override
    public BufferedImage render(RectangleSettings params) {
        BufferedImage image = new BufferedImage(
                params.getWidth(),
                params.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        ImageRendererUtils.renderBackground(params, graphics);
        graphics.setColor(params.getRectColor());
        if (params.isHasFill()) {
            graphics.fillRect(
                    params.getRectX(),
                    params.getRectY(),
                    params.getRectWidth(),
                    params.getRectHeight()
            );
        } else {
            graphics.drawRect(
                    params.getRectX(),
                    params.getRectY(),
                    params.getRectWidth(),
                    params.getRectHeight()
            );
        }
        return image;
    }

    public static class RectangleSettings implements ImageSettings {
        private final int width;
        private final int height;
        private final Color background;
        private final int rectX;
        private final int rectY;
        private final int rectWidth;
        private final int rectHeight;
        private final boolean hasFill;
        private final Color rectColor;

        public RectangleSettings(
                int width,
                int height,
                Color background,
                int rectX,
                int rectY,
                int rectWidth,
                int rectHeight,
                boolean hasFill,
                Color rectColor
        ) {
            this.width = width;
            this.height = height;
            this.background = background;
            this.rectX = rectX;
            this.rectY = rectY;
            this.rectWidth = rectWidth;
            this.rectHeight = rectHeight;
            this.hasFill = hasFill;
            this.rectColor = rectColor;
        }


        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public Color getBackground() {
            return background;
        }

        public int getRectX() {
            return rectX;
        }

        public int getRectY() {
            return rectY;
        }

        public int getRectWidth() {
            return rectWidth;
        }

        public int getRectHeight() {
            return rectHeight;
        }

        public boolean isHasFill() {
            return hasFill;
        }

        public Color getRectColor() {
            return rectColor;
        }
    }

    public static void main(String[] args) throws IOException {
        ImageRendererUtils.saveImage(
                new RectangleRenderer().render(
                        new RectangleSettings(
                                100,
                                100,
                                Color.CYAN,
                                10,
                                10,
                                30,
                                50,
                                true,
                                Color.GRAY
                        )),
                "image"
        );
    }

}
