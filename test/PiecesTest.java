import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PiecesTest {

    private Pieces pieces;
    @Before
    public void createPieces() {
        this.pieces = new Pieces(0);
    }

    @Test
    public void canCreatePieces() {
        assertTrue(this.pieces != null);
    }

    @Test
    public void testCanCreateNonEmptySets() {
        assertTrue(this.pieces.villagerSet.size() > 0 && this.pieces.totoroSet.size() > 0);
    }

    @Test
    public void testCanCreateExactlyTwentyVillager() {
        assertTrue(this.pieces.villagerSet.size() == 20);
    }

    @Test
    public void testCanCreateExactlyThreeTotoro() {
        assertTrue(this.pieces.totoroSet.size() == 3);
    }

    @Test
    public void testCanSetColorOfVillager() {
        Villager meep = this.pieces.villagerSet.get(0);
        assertTrue(meep.getVillagerColor() == "Black");
    }

    @Test
    public void testCanSetColorOfTotoro() {
        Totoro tot = this.pieces.totoroSet.get(0);
        assertTrue(tot.getTotoroColor() == "Blue");
    }

    @Test
    public void testCanPlaceTotoro() {
        Totoro tot = this.pieces.placeTotoro();
        assertTrue(this.pieces.totoroSet.size() == 2);
    }

    @Test
    public void testCanPlaceAllTotoro() {
        for(int numTotoro = 0; numTotoro < 3; numTotoro ++) {
            Totoro tot = this.pieces.placeTotoro();
        }
        assertTrue(this.pieces.totoroSet.size() == 0);
    }

    @Test
    public void testCanPlaceVillager() {
        Villager meep = this.pieces.placeVillager();
        assertTrue(this.pieces.villagerSet.size() == 19);
    }

    @Test
    public void testCanPlaceAllVillager() {
        for(int numVillager = 0; numVillager < 20; numVillager++) {
            Villager meep = this.pieces.placeVillager();
        }
        assertTrue(this.pieces.villagerSet.size() == 0);
    }
}
