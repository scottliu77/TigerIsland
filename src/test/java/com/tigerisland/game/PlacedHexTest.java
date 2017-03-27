package com.tigerisland.game;

import com.tigerisland.game.Hex;
import com.tigerisland.game.Location;
import com.tigerisland.game.PlacedHex;
import com.tigerisland.game.Terrain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlacedHexTest {

    private PlacedHex placedHex;
    private Hex hex;
    private Location location;

    @Before
    public void createPlacedHex() {
        this.hex = new Hex("dummyID", Terrain.GRASSLANDS);
        this.location = new Location(1, 1);
        this.placedHex = new PlacedHex(hex, location);
    }

    @Test
    public void testCanCreatePlacedHex() {
        assertTrue(placedHex != null);
    }

    @Test
    public void testCanGetLocationFromPlacedHex() {
        Location retrievedLocation = placedHex.getLocation();
        assertTrue(retrievedLocation != null);
    }

    @Test
    public void testCanGetHexFromPlacedHex() {
        Hex retrievedHex = placedHex.getHex();
        assertTrue(retrievedHex != null);
    }

    @Test
    public void testCanTellIfHexIsEmpty() {
        assertTrue(placedHex.isEmpty());
    }

    @Test
    public void testCanGetExpansionStatus() { assertTrue(!placedHex.getExpansionStatus()); }

    @Test
    public void testCanChangeExpansionStatus() {
        boolean status = true;
        placedHex.setExpansionStatus(status);
        assertTrue(placedHex.getExpansionStatus());
    }
}
