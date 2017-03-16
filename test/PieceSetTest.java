import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceSetTest {

    private PieceSet pieceSet;

    @Before
    public void createPieces() {
        pieceSet = new PieceSet(Color.BLACK);
    }

    @Test
    public void canCreatePieces() {
        assertTrue(pieceSet != null);
    }

    @Test
    public void testCanCreateNonEmptySets() {
        assertTrue(pieceSet.villagerSet.size() > 0 && pieceSet.totoroSet.size() > 0);
    }

    @Test
    public void testCanCreateExactlyTwentyVillager() {
        assertTrue(pieceSet.villagerSet.size() == 20);
    }

    @Test
    public void testCanCreateExactlyThreeTotoro() {
        assertTrue(pieceSet.totoroSet.size() == 3);
    }

    @Test
    public void testCanSetColorOfVillager() {
        Piece meep = pieceSet.villagerSet.get(0);
        assertTrue(meep.getColorString() == "Black");
    }

    @Test
    public void testCanSetColorOfTotoro() {
        Piece totororo = pieceSet.totoroSet.get(0);
        assertTrue(totororo.getColorString() == "Black");
    }

    @Test
    public void testCanGetNumberOfVillagersRemaining() {
        assertTrue(pieceSet.getNumberOfVillagersRemaining() == pieceSet.villagerSet.size());
    }

    @Test
    public void testCanGetNumberOfTotoroRemaining() {
        assertTrue(pieceSet.getNumberOfTotoroRemaining() == pieceSet.totoroSet.size());
    }

    @Test
    public void testCanPlaceTotoro() {
        Piece totoro = pieceSet.placeTotoro();
        assertTrue(pieceSet.totoroSet.size() == 2);
    }

    @Test
    public void testCanPlaceAllTotoro() {
        for(int numTotoro = 0; numTotoro < 3; numTotoro ++) {
           Piece totoro = pieceSet.placeTotoro();
        }
        assertTrue(pieceSet.totoroSet.size() == 0);
    }

    @Test
    public void testCanPlaceVillager() {
        Piece villager = pieceSet.placeVillager();
        assertTrue(pieceSet.villagerSet.size() == 19);
    }

    @Test
    public void testCanPlaceAllVillager() {
        for(int numVillager = 0; numVillager < 20; numVillager++) {
            Piece villager = pieceSet.placeVillager();
        }
        assertTrue(pieceSet.villagerSet.size() == 0);
    }

    @Test
    public void testCannotPlaceMoreWhenHavingZero() {
        try {
            for (int numVillager = 0; numVillager < 21; numVillager++) {
                Piece villager = pieceSet.placeVillager();
            }
            assertTrue(false);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanCheckInventoryEmpty() {
        for(int numVillager = 0; numVillager < 20; numVillager++) {
            Piece villager = pieceSet.placeVillager();
        }
        for(int numTotoro = 0; numTotoro < 3; numTotoro++) {
            Piece totoro = pieceSet.placeTotoro();
        }
        assertTrue(pieceSet.inventoryEmpty() == true);
    }

    @Test
    public void testCanCheckInventoryNotEmpty() {
        for(int numVillager = 0; numVillager < 2; numVillager++) {
            Piece villager = pieceSet.placeVillager();
        }
        assertTrue(pieceSet.inventoryEmpty() == false);
    }


}
