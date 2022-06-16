package hackday.parse;


import hackday.renderer.ImageRenderer;
import hackday.renderer.ImageSettings;

import java.awt.image.BufferedImage;

public class Parsed {
    private ImageRenderer<ImageSettings> renderer;
    private ImageSettings settings;

    public void setRenderer(ImageRenderer<ImageSettings> renderer) {
        this.renderer = renderer;
    }

    public ImageSettings getSettings() {
        return settings;
    }

    public void setSettings(ImageSettings settings) {
        this.settings = settings;
    }

    public BufferedImage render(){
        return renderer.render(settings);
    }
}
