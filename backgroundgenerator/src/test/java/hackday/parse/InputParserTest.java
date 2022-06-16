package hackday.parse;

import hackday.renderer.ImageRenderer;
import hackday.renderer.ImageSettings;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InputParserTest {

    private InputParser parser;

    @Before
    public void setUp() {
        parser = new InputParser();
    }

    @Test
    public void givenNull_returnDefaults() {
        Parsed parsed = parser.parse(null);
        assertThat(parsed.getSettings().getWidth(), is(1920));
        assertThat(parsed.getSettings().getHeight(), is(1080));
        assertThat(parsed.getSettings().getBackground(), is(Color.BLACK));
    }

    @Test
    public void givenBlank_returnDefaults() {
        Parsed parsed = parser.parse("");
        assertThat(parsed.getSettings().getWidth(), is(1920));
        assertThat(parsed.getSettings().getHeight(), is(1080));
        assertThat(parsed.getSettings().getBackground(), is(Color.BLACK));
    }

    @Test
    public void givenCustomHeight_customHeightPreserved() {
        Parsed parsed = parser.parse("height:100;");
        assertThat(parsed.getSettings().getWidth(), is(1920));
        assertThat(parsed.getSettings().getHeight(), is(100));
        assertThat(parsed.getSettings().getBackground(), is(Color.BLACK));
    }

    @Test
    public void givenCustomWidth_customWidthPreserved() {
        Parsed parsed = parser.parse("width:100;");
        assertThat(parsed.getSettings().getWidth(), is(100));
        assertThat(parsed.getSettings().getHeight(), is(1080));
        assertThat(parsed.getSettings().getBackground(), is(Color.BLACK));
    }

    @Test
    public void givenCustomBackground_customBackgroundPreserved() {
        Parsed parsed = parser.parse("background_color:#FFFFFF;");
        assertThat(parsed.getSettings().getWidth(), is(1920));
        assertThat(parsed.getSettings().getHeight(), is(1080));
        assertThat(parsed.getSettings().getBackground(), is(Color.WHITE));
    }

    @Test(expected = IllegalStateException.class)
    public void givenUnknownFactory_expectException() {
        parser.parse("unknown()");
    }

    @Test
    public void givenRegisteredFactory_expectParametersFactoryFoundAndCalled() {
        SpyFactory spyFactory = new SpyFactory();
        parser.register("my_factory", spyFactory);
        parser.parse("my_factory(a:1,b:2,c:3)");
        assertThat(spyFactory.getCapturedParameters().get("a"), is("1"));
        assertThat(spyFactory.getCapturedParameters().get("b"), is("2"));
    }

    private static class SpyFactory implements RendererFactory<ImageRenderer<ImageSettings>, ImageSettings> {
        private Map<String,String> capturedParameters;
        public ImageRenderer<ImageSettings> create() {
            return null;
        }

        public ImageSettings setting(ImageSettings base, Map<String, String> rawParameters) {
            capturedParameters = rawParameters;
            return null;
        }

        public Map<String, String> getCapturedParameters() {
            return capturedParameters;
        }
    }
}