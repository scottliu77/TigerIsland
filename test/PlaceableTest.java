import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlaceableTest {

    private Placeable villager;
    private Placeable totoro;

    @Before
    public void createPlaceables() {
        this.villager = Placeable.VILLAGER;
        this.totoro = Placeable.TOTORO;
    }

    @Test
    public void testCanCreatePlaceable() {
        assertTrue(this.villager != null);
    }

    @Test
    public void testCanGetPlaceableCode() {
        assertTrue(this.villager.getCode() == 0);
    }

    @Test
    public void testCanGetPlaceablePiece() {
        assertTrue(this.villager.getPiece() == "Villager");
    }

}
