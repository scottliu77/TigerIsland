package com.tigerisland.game;

import com.tigerisland.game.Terrain;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TerrainTest {

    private Terrain terrainType;

    @Before
    public void createTerrainType() {
        this.terrainType = Terrain.GRASSLANDS;
    }

    @Test
    public void testCanCreateTerrain() {
        assertTrue(terrainType != null);
    }

    @Test
    public void testCanGetTerrainString() {
        assertTrue(terrainType.getType() == "Grasslands");
    }
}