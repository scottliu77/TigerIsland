import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MatchTest {

    private Match match;
    private Settings settings;

    @Before
    public void createMatch() {
        this.settings = new Settings();
        this.match = new Match(settings);
    }

    @Test
    public void testCanCreateMatch() {
        assertTrue(match != null);
    }

    @Test
    public void testMatchCreatesTheCorrectNumberOfGames() {
        assertTrue(match.games.size() == settings.gameCount);
    }
}
