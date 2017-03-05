import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeepleTest {

    private Meeple meep;

    @Before
    public void createMeeple() {
        this.meep = new Meeple(MeepleColor.WHITE);
    }

    @Test
    public void testCanCreateMeeple() {
        assertTrue(this.meep != null);
    }

    @Test
    public void testCanGetMeepleColor() {
        assertTrue(this.meep.getMeepleColor() == "White");
    }

    @Test
    public void testCanGetMeepleCode() {
        assertTrue(this.meep.getMeepleCode() == 1);
    }
}

