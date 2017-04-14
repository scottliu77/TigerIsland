package com.tigerisland.game.board;

import com.tigerisland.game.board.Terrain;
import com.tigerisland.game.board.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    private Tile tile;

    @Before
    public void createTile() {
        this.tile = new Tile(Terrain.JUNGLE, Terrain.ROCK);
    }

    @Test
    public void testCanCreateTile() {
        assertTrue(tile != null);
    }

    @Test
    public void testCanGetVolcanoHex() {
        assertTrue(tile.getCenterHex() != null);
    }

    @Test
    public void testCanGetLeftHex() {
        assertTrue(tile.getLeftHex() != null);
    }

    @Test
    public void testCanGetRightHex() {
        assertTrue(tile.getRightHex() != null);
    }

    @Test
    public void testCanGetUniqueId() {
        assertTrue(tile.getTileID() instanceof String);
    }

}
