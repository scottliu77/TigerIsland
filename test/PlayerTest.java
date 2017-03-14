import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player playerWhite;
    private Player playerBlack;

    @Before
    public void createPlayer() {
        this.playerWhite = new Player(PlayerColor.WHITE);
        this.playerBlack = new Player(PlayerColor.BLACK);
    }

    @Test
    public void testCanCreatePlayer() {
        assertTrue(this.playerWhite != null);
    }

    @Test
    public void testCanGetPlayerColor() {
        assertTrue(this.playerWhite.getPlayerColor() == "White");
    }

    @Test
    public void testCanGetPlayerCode() {
        assertTrue(this.playerWhite.getPlayerCode() == 1);
    }

    @Test
    public void testCanGetPlayerScore() {
        assertTrue(this.playerWhite.getScore() == 0);
    }

}
