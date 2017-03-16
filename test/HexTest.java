import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class HexTest {

    private Hex hex;

    @Before
    public void createHex() {
        this.hex = new Hex("dummyID" , Terrain.VOLCANO);
    }

    @Test
    public void testCanCreateHexUsingIDandTerrain() {
        assertTrue(this.hex != null);
    }

    @Test
    public void testCanCreateDummyHexUsingNoParameters() {
        Hex dummyHex = new Hex();
        assertTrue(dummyHex != null);
    }

    @Test
    public void testCanCreateHexUsingIDTerrainAndHeight() {
        Hex triParameterHex = new Hex("dummyID", Terrain.GRASSLANDS, 0);
        assertTrue(triParameterHex != null);
    }

    @Test
    public void testCanGetHexTerrain() {
        assertTrue(this.hex.getHexTerrain() == Terrain.VOLCANO);
    }

    @Test
    public void testCanGetHexHeight() {
        assertTrue(this.hex.getHeight() == 1);
    }

    @Test
    public void testCanGetHexContentType() {
        assertTrue(this.hex.getPieceType().equals("Empty"));
    }

    @Test
    public void testCanGetNonEmptyContentType() {
        this.hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        assertTrue(this.hex.getPieceType().equals("Totoro"));
    }

    @Test
    public void testCanGetHexContentCount() {
        assertTrue(this.hex.getPieceCount() == 0);
    }

    @Test
    public void testCanGetHexID() {
        assertTrue(this.hex.getID().equals("dummyID"));
    }

    @Test
    public void testCanGetHexIDsubstring() {
        assertTrue(this.hex.getIDfirstChars(2).equals("du"));
    }

    @Test
    public void testCanAddVillagerToHex() {
        this.hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.VILLAGER), 1);
        assertTrue(this.hex.getPieceType() == "Villager" && this.hex.getPieceCount() == 1);
    }

    @Test
    public void testCanAddTotoroToHex() {
        this.hex.addPiecesToHex(new Piece(Color.BLACK, PieceType.TOTORO), 1);
        assertTrue(this.hex.getPieceType() == "Totoro" && this.hex.getPieceCount() == 1);
    }


}
