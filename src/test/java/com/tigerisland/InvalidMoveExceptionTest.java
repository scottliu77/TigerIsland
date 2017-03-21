package com.tigerisland;

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
            throw new InvalidMoveException("You can't make that currentMove!");
        } catch(InvalidMoveException exception) {
            assertTrue(true);
        }
    }
}
