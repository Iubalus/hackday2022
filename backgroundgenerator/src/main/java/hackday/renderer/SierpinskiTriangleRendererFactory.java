package hackday.renderer;

import hackday.parse.RendererFactory;

import java.awt.*;
import java.util.Map;

public class SierpinskiTriangleRendererFactory implements RendererFactory<SierpinskiTriangleRenderer, SierpinskiTriangleRenderer.R> {
    public SierpinskiTriangleRenderer create() {
        return new SierpinskiTriangleRenderer();
    }

    public SierpinskiTriangleRenderer.R setting(
            ImageSettings base, Map<String, String> rawParameters
    ) {
        return new SierpinskiTriangleRenderer.R(
                base,
                Color.decode(rawParameters.get("color")),
                Integer.parseInt(rawParameters.get("depth")),
                Integer.parseInt(rawParameters.get("base"))
        );
    }
}
