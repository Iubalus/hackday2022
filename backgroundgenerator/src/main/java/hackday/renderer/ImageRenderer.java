package hackday.renderer;

import java.awt.image.BufferedImage;

public interface ImageRenderer<T extends ImageSettings> {

    BufferedImage render(T params);

}
