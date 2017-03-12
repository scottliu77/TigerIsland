import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    Location location;

    @Before
    public void createLocation() {
        location = new Location(3,4);
    }

    @Test
    public void testCanCreateLocation() {
        assertTrue(this.location != null);
    }

    @Test
    public void testCorrectLocationValues() {
        assertTrue((this.location.x == 3) && (this.location.y == 4));
    }
}
