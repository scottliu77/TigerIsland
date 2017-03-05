import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TotoroTest {

    private Totoro tot;

    @Before
    public void createMeeple() {
        this.tot = new Totoro(TotoroColor.BLUE);
    }

    @Test
    public void testCanCreateTotoro() {
        assertTrue(this.tot != null);
    }

    @Test
    public void testCanGetTotoroColor() {
        assertTrue(this.tot.getTotoroColor() == "Blue");
    }

    @Test
    public void testCanGetTotoroCode() {
        assertTrue(this.tot.getTotoroCode() == 0);
    }
}

