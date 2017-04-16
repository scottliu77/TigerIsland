package com.tigerisland.game.board;

import com.tigerisland.game.board.Terrain;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TerrainTest {

    private Terrain terrainType;

    @Before
    public void createTerrainType() {
        this.terrainType = Terrain.GRASS;
    }

    @Test
    public void testCanCreateTerrain() {
        assertTrue(terrainType != null);
    }

    @Test
    public void testCanGetTerrainString() {
        assertTrue(terrainType.getTerrainString() == "Grass");
    }

    @Test
    public void testTotalOfSixTerrainsExist() {
        assertTrue(Terrain.values().length == 6);
    }
}
