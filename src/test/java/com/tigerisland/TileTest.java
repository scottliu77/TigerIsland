package com.tigerisland;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    private Tile tile;

    @Before
    public void createTile() {
        this.tile = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
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
        assertTrue(tile.getUniqueID() instanceof String);
    }

}
