import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void createGame() {
        this.game = new Game(2);
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(this.game != null);
    }
}
