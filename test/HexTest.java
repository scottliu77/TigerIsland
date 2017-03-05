import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class HexTest {

    private Hex hex;

    @Before
    public void createHex() {
        this.hex = new Hex(Terrain.VOLCANO);
    }

    @Test
    public void testCanCreateHex() {
        assertTrue(this.hex != null);
    }

    @Test
    public void testCanGetHexType() {
        assertTrue(this.hex.getHexTerrain() == Terrain.VOLCANO);
    }


//
//    @Test
//    public void testCheckHexContents() {
//
//    }
}
