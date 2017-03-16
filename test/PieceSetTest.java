import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceSetTest {

    private PieceSet pieceSet;

    @Before
    public void createPieces() {
        this.pieceSet = new PieceSet(Color.BLACK);
    }

    @Test
    public void canCreatePieces() {
        assertTrue(this.pieceSet != null);
    }

    @Test
    public void testCanCreateNonEmptySets() {
        assertTrue(this.pieceSet.villagerSet.size() > 0 && this.pieceSet.totoroSet.size() > 0);
    }

    @Test
    public void testCanCreateExactlyTwentyVillager() {
        assertTrue(this.pieceSet.villagerSet.size() == 20);
    }

    @Test
    public void testCanCreateExactlyThreeTotoro() {
        assertTrue(this.pieceSet.totoroSet.size() == 3);
    }

    @Test
    public void testCanSetColorOfVillager() {
        Piece meep = this.pieceSet.villagerSet.get(0);
        assertTrue(meep.getColorString() == "Black");
    }

    @Test
    public void testCanSetColorOfTotoro() {
        Piece totororo = this.pieceSet.totoroSet.get(0);
        assertTrue(totororo.getColorString() == "Black");
    }

    @Test
    public void testCanGetNumberOfVillagersRemaining() {
        assertTrue(pieceSet.getNumberOfVillagersRemaining() == this.pieceSet.villagerSet.size());
    }

    @Test
    public void testCanGetNumberOfTotoroRemaining() {
        assertTrue(pieceSet.getNumberOfTotoroRemaining() == this.pieceSet.totoroSet.size());
    }

    @Test
    public void testCanPlaceTotoro() {
        Piece totoro = this.pieceSet.placeTotoro();
        assertTrue(this.pieceSet.totoroSet.size() == 2);
    }

    @Test
    public void testCanPlaceAllTotoro() {
        for(int numTotoro = 0; numTotoro < 3; numTotoro ++) {
           Piece totoro = this.pieceSet.placeTotoro();
        }
        assertTrue(this.pieceSet.totoroSet.size() == 0);
    }

    @Test
    public void testCanPlaceVillager() {
        Piece villager = this.pieceSet.placeVillager();
        assertTrue(this.pieceSet.villagerSet.size() == 19);
    }

    @Test
    public void testCanPlaceAllVillager() {
        for(int numVillager = 0; numVillager < 20; numVillager++) {
            Piece villager = this.pieceSet.placeVillager();
        }
        assertTrue(this.pieceSet.villagerSet.size() == 0);
    }
}
