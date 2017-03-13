import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BoardTest{
    private Board board;

    @Before
    public void createBoard() {
        this.board = new Board();
    }

    @Test
    public void testCanCreateBoard() {
        assertTrue(this.board != null);
    }

    @Test
    public void testMapWorksCorrectly() {
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);
        board.printMap();
    }

    @Test
    public void notAvailableLocation() {
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Location location2 = new Location(0,0);
        assertFalse(board.isAnAvailableSpace(location2));
    }

    @Test
    public void hexAtLocation(){
        Tile tile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location = new Location(0,0);
        board.placeTile(tile, location,0);

        Tile tile2 = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Location location2 = new Location(2,1);
        assertFalse(board.hexExistsAtLocation(location2));
    }
}