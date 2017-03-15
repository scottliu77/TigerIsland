import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MatchTest {

    private Match match;

    @Before
    public void createMatch() {
        this.match = new Match(true);
    }

    @Test
    public void testCanCreateMatch() {
        assertTrue(this.match != null);
    }
}
