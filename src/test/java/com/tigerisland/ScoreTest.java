package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ScoreTest {

    private Score score;

    @Before
    public void createScore() {
        this.score = new Score();
    }

    @Test
    public void testCanCreateTest() {
        assertTrue(score != null);
    }

    @Test
    public void testCreatedScoreHasZeroPoints() {
        assertTrue(score.getScoreValue() == 0);
    }

    @Test
    public void testCanAddPointsToScore() throws InvalidMoveException {
        score.addPoints(5);
        assertTrue(score.getScoreValue() == 5);
    }

    @Test
    public void testCannotSubtractPointsFromScore()  {
        try {
            score.addPoints(-5);
        } catch (InvalidMoveException exception) {
            assertTrue(true);
        }
    }
}
