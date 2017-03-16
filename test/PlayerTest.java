import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player playerWhite;

    @Before
    public void createPlayer() {
        this.playerWhite = new Player(Color.WHITE);
    }

    @Test
    public void testCanCreatePlayer() {
        assertTrue(this.playerWhite != null);
    }

    @Test
    public void testCanGetPlayerColor() {
        assertTrue(this.playerWhite.getPlayerColor().getColor() == "White");
    }

    @Test
    public void testCanGetPlayerScore() {
        assertTrue(this.playerWhite.score.getScore() == 0);
    }


}
