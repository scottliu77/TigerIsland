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
    public void testCanAddHexToBoard() {
        this.board.addHexToMap(0,0, new Hex(Terrain.ROCKY));
        assertTrue(this.board.boardMap[0][0] != null);
    }

    @Test
    public void testCanAddTileToBoard() {
        int volcano_x = 5;
        int volcano_y = 5;
        int left_x = 5;
        int left_y = 6;
        int right_x = 6;
        int right_y = 6;
        Tile newTile = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        this.board.addTileToMap(volcano_x, volcano_y, left_x, left_y, right_x, right_y , newTile);
        assertTrue(this.board.boardMap[volcano_x][volcano_y] != null &&
            this.board.boardMap[left_x][left_y] != null &&
            this.board.boardMap[right_x][right_y] != null);
    }
}