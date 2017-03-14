import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerColorTest {
    private PlayerColor color;

    @Before
    public void createPlayerColor() {
        this.color = PlayerColor.BLACK;
    }

    @Test
    public void testCanCreatePlayerColor() {
        assertTrue(this.color != null);
    }

    @Test
    public void testCanGetColorCode() {
        assertTrue(this.color.getCode() == 0);
    }

    @Test
    public void testCanGetColorString() {
        assertTrue(this.color.getColor() == "Black");
    }

}
