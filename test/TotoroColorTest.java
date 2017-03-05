import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TotoroColorTest {

    private TotoroColor color;

    @Before
    public void createTotoroColor() {
        this.color = TotoroColor.BLUE;
    }

    @Test
    public void testCanCreateTotoroColor() {
        assertTrue(this.color != null);
    }

    @Test
    public void testCanGetColorCode() {
        assertTrue(this.color.getCode() == 0);
    }

    @Test
    public void testCanGetColorString() {
        assertTrue(this.color.getColor() == "Blue");
    }
}
