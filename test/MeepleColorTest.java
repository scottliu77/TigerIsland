import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeepleColorTest {

    private MeepleColor color;

    @Before
    public void createMeepleColor() {
        this.color = MeepleColor.BLACK;
    }

    @Test
    public void testCanCreateMeepleColor() {
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
