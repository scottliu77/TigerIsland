package com.tigerisland.messenger;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import junit.framework.Assert;
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
        dummyTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
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
}
