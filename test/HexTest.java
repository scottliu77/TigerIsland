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
        assertTrue(this.hex.getHeight() == 0);
    }

    @Test
    public void testCanGetHexContentType() {
        assertTrue(this.hex.getContentType().equals("Empty"));
    }

    @Test
    public void testCanGetNonEmptyContentType() {
        this.hex.addPiecesToHex(Placeable.TOTORO, 1);
        assertTrue(this.hex.getContentType().equals("Totoro"));
    }

    @Test
    public void testCanGetHexContentCount() {
        assertTrue(this.hex.getContentCount() == 0);
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
        this.hex.addPiecesToHex(Placeable.VILLAGER, 1);
        assertTrue(this.hex.getContentType() == "Villager" && this.hex.getContentCount() == 1);
    }

    @Test
    public void testCanAddTotoroToHex() {
        this.hex.addPiecesToHex(Placeable.TOTORO, 1);
        assertTrue(this.hex.getContentType() == "Totoro" && this.hex.getContentCount() == 1);
    }


}
