import org.junit.Test;
import org.junit.Before;

import java.util.Set;

import static org.junit.Assert.*;

public class GameTest {

    private Settings settings = new Settings();
    private Game game;

    @Before
    public void createGame() {
        this.game = new Game(settings);
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(this.game != null);
    }
}
