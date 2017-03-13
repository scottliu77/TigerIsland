import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TimerTest {

    private final int MAXSECONDS = 10;
    private Timer timer;

    @Before
    public void createTimer() {
        this.timer = new Timer(this.MAXSECONDS);
    }

    @Test
    public void testCanCreateTimer() {
        assertTrue(timer != null);
    }
}
