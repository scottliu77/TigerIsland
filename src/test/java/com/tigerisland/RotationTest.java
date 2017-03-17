package com.tigerisland;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class RotationTest {

    private Rotation rotation;

    @Before
    public void createHex() {
        this.rotation = new Rotation();
    }

    @Test
    public void testCanCreateRotation() {
        assertTrue(rotation != null);
    }

    @Test
    public void testCanRotateZeroDegrees() {
        Location rotatedZeroDegrees = Rotation.calculateRotation(0);
        Location locationZeroRotate = new Location(1, 0);
        assertTrue(locationZeroRotate.x == rotatedZeroDegrees.x && locationZeroRotate.y == rotatedZeroDegrees.y);
    }

    @Test
    public void testCanRotateSixtyDegrees() {
        Location rotatedSixtyDegrees = Rotation.calculateRotation(60);
        Location locationSixtyRotate = new Location(0, 1);
        assertTrue(locationSixtyRotate.x == rotatedSixtyDegrees.x && locationSixtyRotate.y == rotatedSixtyDegrees.y);
    }

    @Test
    public void testCanRotateOneTwentyDegrees() {
        Location rotatedOneTwentyDegrees = Rotation.calculateRotation(120);
        Location locationOneTwentyRotate = new Location(-1, 1);
        assertTrue(locationOneTwentyRotate.x == rotatedOneTwentyDegrees.x && locationOneTwentyRotate.y == rotatedOneTwentyDegrees.y);
    }

    @Test
    public void testCanRotateOneEightyDegrees() {
        Location rotatedOneEightyDegrees = Rotation.calculateRotation(180);
        Location locationOneEightyRotate = new Location(-1, 0);
        assertTrue(locationOneEightyRotate.x == rotatedOneEightyDegrees.x && locationOneEightyRotate.y == rotatedOneEightyDegrees.y);
    }

    @Test
    public void testCanRotateTwoFourtyDegrees() {
        Location rotatedTwoFourtyDegrees = Rotation.calculateRotation(240);
        Location locationTwoFourtyRotate = new Location(0, -1);
        assertTrue(locationTwoFourtyRotate.x == rotatedTwoFourtyDegrees.x && locationTwoFourtyRotate.y == rotatedTwoFourtyDegrees.y);
    }

    @Test
    public void testCanRotateThreeHundredDegrees() {
        Location rotatedThreeHundredDegrees = Rotation.calculateRotation(300);
        Location locationThreeHundredRotate = new Location(1, -1);
        assertTrue(locationThreeHundredRotate.x == rotatedThreeHundredDegrees.x && locationThreeHundredRotate.y == rotatedThreeHundredDegrees.y);
    }

    @Test
    public void testRotatingThreeSixtyEqualsZero() {
        Location rotatedThreeSixtyDegrees = Rotation.calculateRotation(360);
        Location rotatedZeroDegrees = Rotation.calculateRotation(0);
        assertTrue(rotatedThreeSixtyDegrees.x == rotatedZeroDegrees.x && rotatedThreeSixtyDegrees.y == rotatedZeroDegrees.y);
    }

}
