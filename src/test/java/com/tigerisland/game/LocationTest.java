package com.tigerisland.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LocationTest {

    private final ByteArrayOutputStream outText = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errText = new ByteArrayOutputStream();

    private Location location;
    private Location newLocation;

    private PrintStream stdout;
    private PrintStream errout;

    @Before
    public void createLocation() {
        this.location = new Location(3, 4);
        this.newLocation = new Location(1, 1);
    }

    @Before
    public void setupStreams() {
        stdout = System.out;
        System.setOut(new PrintStream(outText));

        errout = System.err;
        System.setErr(new PrintStream(errText));
    }

    @After
    public void cleanupStreams() {
        System.setOut(stdout);
        System.setErr(errout);
    }

    @Test
    public void testCanCreateLocationUsingCoordinates() {
        assertTrue(location != null);
    }

    @Test
    public void testCanCreateLocationUsingLocation() {
        Location locationBasedLocation = new Location((location));
        assertTrue(locationBasedLocation instanceof Location);
    }

    @Test
    public void testStoresCorrectLocationValues() {
        assertTrue((location.x == 3) && (location.y == 4));
    }

    @Test
    public void testCanProperlyAddLocationValuesUsingTwoLocations() {
        Location summedLocation = Location.add(location, newLocation);
        assertTrue(summedLocation.x == 4 && summedLocation.y == 5);
    }

    @Test
    public void testCanRotateLocationByAdding() {
        Location rotatedLocation = Location.rotateHexLeft(location, 0);
        assertTrue(rotatedLocation.x == location.x + 1 && rotatedLocation.y == location.y);
    }

    @Test
    public void testCanEvaluateGreaterThan() {
        assertTrue(location.greaterThan(newLocation));
    }

    @Test
    public void testCanEvaluteLessThan() {
        assertTrue(newLocation.lessThan(location));
    }

    @Test
    public void testCanEvaluateEqualTo() {
        assertTrue(location.equalTo(location));
    }

    @Test
    public void testCanCreateLocationList() {
        ArrayList<Location> locationList = new ArrayList<Location>();
        locationList.add(location);
        Location.printLocations(locationList);

        String outTextNewlineStripped = outText.toString().replace("\n", "").replace("\r", "");
        assertEquals("X=3 : Y=4", outTextNewlineStripped);
    }

    @Test
    public void testCanCreateLocationAdjacentLocList() {
        assertTrue(location.getAdjacentLocations() != null);
    }

    @Test
    public void testThereShouldAlwaysBeSixAdjacentLocations() {
        assertTrue(location.getAdjacentLocations().size() == 6);
    }

    @Test
    public void testALocationIsEqualToAnotherLocation() {
        Location loc1 = new Location(0,0);
        Location loc2 = new Location(0,0);
        assertTrue(loc1.equals(loc2));
    }

    @Test
    public void testTwoLocationsAreNotEqual() {
        Location loc1 = new Location(0,0);
        Location loc2 = new Location (0,1);
        assertFalse(loc1.equals(loc2));
    }
}
