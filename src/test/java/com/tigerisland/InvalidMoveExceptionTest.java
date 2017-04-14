package com.tigerisland;

import com.tigerisland.game.InvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InvalidMoveExceptionTest {

    @Test
    public void testCanThrowInvalidMoveException() {
        try {
            throw new InvalidMoveException();
        } catch(InvalidMoveException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanThrowInvalidMoveExceptionWithMessage() {
        try {
            throw new InvalidMoveException("You can't make that currentBuildAction!");
        } catch(InvalidMoveException exception) {
            assertTrue(true);
        }
    }
}
