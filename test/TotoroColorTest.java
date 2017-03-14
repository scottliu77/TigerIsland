import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TotoroColorTest {

    private TotoroColor colorBlue;

    @Before
    public void createTotoroColor() {
        this.colorBlue = TotoroColor.BLUE;
    }

    @Test
    public void testCanCreateTotoroColor() {
        assertTrue(this.colorBlue != null);
    }

    @Test
    public void testCanGetColorCode() {
        assertTrue(this.colorBlue.getCode() == 0);
    }

    @Test
    public void testCanGetColorString() {
        assertTrue(this.colorBlue.getColor() == "Blue");
    }
}
