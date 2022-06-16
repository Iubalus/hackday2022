package hackday.parse;

import hackday.renderer.ExampleRenderer;
import hackday.renderer.ImageSettings;

import java.util.Map;

public class ExampleRendererFactory implements InputParser.RendererFactory<ExampleRenderer, ExampleRenderer.R> {

    public ExampleRenderer create() {
        return new ExampleRenderer();
    }

    public ExampleRenderer.R setting(ImageSettings base, Map<String, String> rawParameters) {
        return new ExampleRenderer.R(base.getWidth(), base.getHeight(), base.getBackground());
    }
}
