package hackday.renderer;

import hackday.parse.RendererFactory;

import java.util.Map;

public class ExampleRendererFactory implements RendererFactory<ExampleRenderer, ExampleRenderer.R> {

    public ExampleRenderer create() {
        return new ExampleRenderer();
    }

    public ExampleRenderer.R setting(ImageSettings base, Map<String, String> rawParameters) {
        return new ExampleRenderer.R(base.getWidth(), base.getHeight(), base.getBackground());
    }
}
