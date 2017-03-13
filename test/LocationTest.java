import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LocationTest {
    Location location;
    Location newLocation;

    private final ByteArrayOutputStream outText = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errText = new ByteArrayOutputStream();

    @Before
    public void createLocation() {
        this.location = new Location(3, 4);
        this.newLocation = new Location(1, 1);
    }

    @Before
    public void setupStreams() {
        System.setOut(new PrintStream(outText));
        System.setErr(new PrintStream(errText));
    }

    @After
    public void cleanupStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testCanCreateLocationUsingCoordinates() {
        assertTrue(this.location != null);
    }

    @Test
    public void testCanCreateLocationUsingLocation() {
        Location locationBasedLocation = new Location((this.location));
        assertTrue(locationBasedLocation instanceof Location);
    }

    @Test
    public void testStoresCorrectLocationValues() {
        assertTrue((this.location.x == 3) && (this.location.y == 4));
    }

    @Test
    public void testCanProperlyAddLocationValuesUsingTwoLocations() {
        Location summedLocation = Location.add(this.location, this.newLocation);
        assertTrue(summedLocation.x == 4 && summedLocation.y == 5);
    }

    @Test
    public void testCanRotateLocationByAdding() {
        Location rotatedLocation = Location.rotateHexRight(this.location, 0);
        assertTrue(rotatedLocation.x == this.location.x + 1 && rotatedLocation.y == this.location.y);
    }

    @Test
    public void testCanEvaluateGreaterThan() {
        assertTrue(this.location.greaterThan(this.newLocation));
    }

    @Test
    public void testCanEvaluteLessThan() {
        assertTrue(this.newLocation.lessThan(this.location));
    }

    @Test
    public void testCanEvaluateEqualTo() {
        assertTrue(this.location.equalTo(this.location));
    }

    @Test
    public void testCanCreateLocationList() {
        ArrayList<Location> locationList = new ArrayList<Location>();
        locationList.add(this.location);
        Location.printLocations(locationList);

        String outTextNewlineStripped = this.outText.toString().replace("\n", "").replace("\r", "");
        assertEquals("X=3 : Y=4", outTextNewlineStripped);
    }


}
