import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GUITest {

    private GUI gui;

    @Before
    public void createGUI() {
        this.gui = new GUI();
    }

    @Test
    public void testCanCreateGUI() {
        assertTrue(this.gui != null);
    }
}
