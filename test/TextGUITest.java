import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextGUITest {

    private TextGUI textGUI;
    private Board board;
    private Tile tile;
    private Location location;

    @Before
    public void createBasicMocks() {
        this.textGUI = new TextGUI();
        this.board = new Board();
        this.tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        this.location = new Location(0,0);
    }

    @Test
    public void testCanCreateTextGUI() {
        assertTrue(this.textGUI != null);
    }

    @Test
    public void testCanPrintPlacedHexLocations() {
        this.board.placeTile(this.tile, this.location, 0);
        TextGUI.printPlacedHexLocations(this.board.locationsOfPlacedHexes());
    }

    @Test
    public void testCanPrintEdgeSpaces() {
        this.board.placeTile(this.tile, this.location, 0);
        TextGUI.printEdgeSpaces(this.board.edgeSpaces);
    }

    @Test
    public void testCanPrintPlacedHexTiles() {
        this.board.placeTile(this.tile, this.location, 0);
        TextGUI.printPlacedHexTiles(this.board.hexesOfPlacedHexes());
    }

    @Test
    public void testCanPrintMap() {
        this.board.placeTile(this.tile, this.location, 0);
        TextGUI.printMap(this.board.locationsOfPlacedHexes(), this.board.edgeSpaces, this.board.hexesOfPlacedHexes());
    }



}
