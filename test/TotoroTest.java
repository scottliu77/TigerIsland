import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TotoroTest {

    private Totoro totoroBlue;
    private Totoro totoroBrown;

    @Before
    public void createVillager() {
        this.totoroBlue = new Totoro(TotoroColor.BLUE);
        this.totoroBrown = new Totoro(TotoroColor.BROWN);
    }

    @Test
    public void testCanCreateTotoro() {
        assertTrue(this.totoroBlue != null);
    }

    @Test
    public void testCanGetTotoroColorBlue() {
        assertTrue(this.totoroBlue.getTotoroColor() == "Blue");
    }

    @Test
    public void testCanGetTotoroColorBrown() {
        assertTrue(this.totoroBrown.getTotoroColor() == "Brown");
    }

    @Test
    public void testCanGetTotoroCodeBlue() {
        assertTrue(this.totoroBlue.getTotoroCode() == 0);
    }

    @Test
    public void testCanGetTotoroCodeBrown() {
        assertTrue(this.totoroBrown.getTotoroCode() == 1);
    }

    @Test
    public void testCanCreateTotoroViaCodeZero() {
        Totoro testTotoro = new Totoro(0);
        assertTrue(testTotoro != null);
    }
    @Test
    public void testCanCreateTotoroViaCodeOne() {
        Totoro testTotoro = new Totoro(1);
        assertTrue(testTotoro != null);
    }

}

