package com.tigerisland.messenger;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AdpaterTest {

    private Board board;
    private Tile dummyTile;
    private Location dummyLocation;
    private int code;
    private int degrees;

    @Before
    public void createBoard() {
        board = new Board();
        dummyTile = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        dummyLocation = new Location(0, 0);
    }

    @Test
    public void testCanConvertOrientationCodeToDegrees() throws InvalidMoveException {
        code = 1;
        degrees = Adapter.convertOrientationToDegrees(code);
        board.placeTile(dummyTile, dummyLocation, degrees);
        assertTrue(board.hexExistsAtLocation(new Location(0, 1)) && board.hexExistsAtLocation(new Location(-1, 1)));
    }

    @Test
    public void testCanConvertOrientationDegreesToCode() {
        degrees = 60;
        assertTrue(Adapter.convertOrientationToCode(60) == 1);
    }

    @Test
    public void testCanConvertAxialToCubeAt0() {
        Location target = new Location(0, 0, 0);
        Location converted = Adapter.convertLocationAxialToCube(new Location(0, 0));
        assertTrue(converted.x == target.x && converted.y == target.y && converted.z == target.z);
    }

    @Test
    public void testCanConvertAxialToCubeTest2() {
        Location target = new Location(-3, 0, 3);
        Location converted = Adapter.convertLocationAxialToCube(new Location(-3, 3));
        assertTrue(converted.x == target.x && converted.y == target.y && converted.z == target.z);
    }

    @Test
    public void testCanConvertAxialToCubeTest3() {
        Location target = new Location(3, -2, -1);
        Location converted = Adapter.convertLocationAxialToCube(new Location(1, -3));
        assertTrue(converted.x == target.x && converted.y == target.y && converted.z == target.z);
    }

    @Test
    public void testCanConvertAxialToCubeTest4() {
        Location target = new Location(1, 2, -3);
        Location converted = Adapter.convertLocationAxialToCube(new Location(3, -1));
        assertTrue(converted.x == target.x && converted.y == target.y && converted.z == target.z);
    }

    @Test
    public void testCanConvertAxialToCubeTest5() {
        Location target = new Location(-2, 3, -1);
        Location converted = Adapter.convertLocationAxialToCube(new Location(1, 2));
        assertTrue(converted.x == target.x && converted.y == target.y && converted.z == target.z);
    }

    @Test
    public void testCanConvertCubeToAxialAt0() {
        Location target = new Location(0, 0);
        Location converted = Adapter.convertLocationCubeToAxial(new Location(0, 0, 0));
        assertTrue(converted.x == target.x && converted.y == target.y);
    }

    @Test
    public void testCanConvertCubeToAxialTest1() {
        Location target = new Location(-2, -1);
        Location converted = Adapter.convertLocationCubeToAxial(new Location(1, -3, 2));
        assertTrue(converted.x == target.x && converted.y == target.y);
    }

    @Test
    public void testCanConvertCubeToAxialTest2() {
        Location target = new Location(0, 3);
        Location converted = Adapter.convertLocationCubeToAxial(new Location(-3, 3, 0));
        assertTrue(converted.x == target.x && converted.y == target.y);
    }

    @Test
    public void testCanConvertCubeToAxialTest3() {
        Location target = new Location(3, -2);
        Location converted = Adapter.convertLocationCubeToAxial(new Location(2, 1, -3));
        assertTrue(converted.x == target.x && converted.y == target.y);
    }

    @Test
    public void testCanConvertCubeToAxialTest4() {
        Location target = new Location(-2, 3);
        Location converted = Adapter.convertLocationCubeToAxial(new Location(-3, 1, 2));
        assertTrue(converted.x == target.x && converted.y == target.y);
    }
}
