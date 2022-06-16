package hackday.parse;

import hackday.renderer.ImageRenderer;
import hackday.renderer.ImageSettings;

import java.util.Map;

public interface RendererFactory<Renderer extends ImageRenderer, Setting extends ImageSettings> {
    Renderer create();

    Setting setting(ImageSettings base, Map<String, String> rawParameters);
}
