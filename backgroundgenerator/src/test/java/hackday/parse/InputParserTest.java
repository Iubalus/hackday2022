package hackday.parse;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class InputParserTest {

    private InputParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new InputParser();
    }

    @Test
    public void givenNull_returnDefaults() {
        Parsed parsed = parser.parse(null);
        assertThat(parsed.getWidth(), is(1920));
        assertThat(parsed.getHeight(), is(1080));
        assertThat(parsed.getBackgroundColor(), is(Color.BLACK));
    }

    @Test
    public void givenBlank_returnDefaults() {
        Parsed parsed = parser.parse("");
        assertThat(parsed.getWidth(), is(1920));
        assertThat(parsed.getHeight(), is(1080));
        assertThat(parsed.getBackgroundColor(), is(Color.BLACK));
    }

    @Test
    public void givenCustomHeight_customHeightPreserved() {
        Parsed parsed = parser.parse("height:100;");
        assertThat(parsed.getWidth(), is(1920));
        assertThat(parsed.getHeight(), is(100));
        assertThat(parsed.getBackgroundColor(), is(Color.BLACK));
    }

    @Test
    public void givenCustomWidth_customWidthPreserved() {
        Parsed parsed = parser.parse("width:100;");
        assertThat(parsed.getWidth(), is(100));
        assertThat(parsed.getHeight(), is(1080));
        assertThat(parsed.getBackgroundColor(), is(Color.BLACK));
    }

    @Test
    public void givenCustomBackground_customBackgroundPreserved() {
        Parsed parsed = parser.parse("background_color:#FFFFFF;");
        assertThat(parsed.getWidth(), is(1920));
        assertThat(parsed.getHeight(), is(1080));
        assertThat(parsed.getBackgroundColor(), is(Color.WHITE));
    }
}