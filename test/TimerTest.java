import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TimerTest {

    Timer timer;

    @Before
    public void createTimer() {
        this.timer = new Timer();
    }

    @Test
    public void testCanCreateTimer() {
        assertTrue(this.timer != null);
    }
}
