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
        assertTrue(this.tile != null);
    }

    @Test
    public void testCanGetVolcanoHex() {
        assertTrue(this.tile.getCenterHex() != null);
    }

    @Test
    public void testCanGetLeftHex() {
        assertTrue(this.tile.getLeftHex() != null);
    }

    @Test
    public void testCanGetRightHex() {
        assertTrue(this.tile.getRightHex() != null);
    }

    @Test
    public void testCanGetUniqueId() {
        assertTrue(this.tile.getUniqueID() instanceof String);
    }

}
