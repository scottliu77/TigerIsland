import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VillagerColorTest {

    private VillagerColor color;

    @Before
    public void createVillagerColor() {
        this.color = VillagerColor.BLACK;
    }

    @Test
    public void testCanCreateVillagerColor() {
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
