package hackday.parse;

import hackday.renderer.ImageRenderer;
import hackday.renderer.ImageSettings;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InputParser {

    private final Map<String, RendererFactory<?, ?>> factories = new HashMap<>();

    public void register(String name, RendererFactory<?, ?> factory) {
        factories.put(name, factory);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Parsed parse(String raw) {
        BaseSetting base = new BaseSetting();
        Parsed result = new Parsed();
        result.setSettings(base);
        if (raw == null || raw.isEmpty()) {
            return result;
        }
        String[] statements = raw.split(";");
        for (String statement : statements) {
            if (!statement.contains("(")) {
                String[] parts = statement.split(":");
                if (parts[0].equalsIgnoreCase("height")) {
                    base.setHeight(Integer.parseInt(parts[1]));
                }
                if (parts[0].equalsIgnoreCase("width")) {
                    base.setWidth(Integer.parseInt(parts[1]));
                }
                if (parts[0].equalsIgnoreCase("background")) {
                    base.setBackground(Color.decode(parts[1]));
                }
            } else {
                String[] parts = statement.split("\\(");
                String params = parts[1].substring(0, parts[1].length() - 1);
                RendererFactory<?, ?> factory = findFactory(parts[0]);
                result.setRenderer((ImageRenderer<ImageSettings>) factory.create());
                result.setSettings(factory.setting(base, toParameterMap(params)));
            }
        }
        return result;
    }

    private RendererFactory<?, ?> findFactory(String part) {
        String name = part.toLowerCase();
        RendererFactory<?, ?> factory = factories.get(name);
        if (factory == null) {
            throw new IllegalStateException("Unknown factory " + name);
        }
        return factory;
    }

    static class BaseSetting implements ImageSettings {

        private int width = 1920;
        private int height = 1080;
        private Color background = Color.BLACK;

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setBackground(Color background) {
            this.background = background;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Color getBackground() {
            return background;
        }
    }

    private Map<String, String> toParameterMap(String rawParameters) {
        Map<String, String> parameters = new HashMap<>();
        if(rawParameters == null || rawParameters.isEmpty()){
            return parameters;
        }
        String[] parameter = rawParameters.split(",");
        for (String s : parameter) {
            final String[] keyValuePair = s.split(":");
            parameters.put(keyValuePair[0], keyValuePair[1]);
        }
        return parameters;
    }

    interface RendererFactory<Renderer extends ImageRenderer<Setting>, Setting extends ImageSettings> {
        Renderer create();

        Setting setting(ImageSettings base, Map<String, String> rawParameters);
    }

}
