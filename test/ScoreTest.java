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
        assertTrue(score.getScore() == 0);
    }

    @Test
    public void testCanAddPointsToScore() {
        score.addPoints(5);
        assertTrue(score.getScore() == 5);
    }

    @Test
    public void testCannotSubtractPointsFromScore() {
        score.addPoints(-5);
        assertTrue(score.getScore() == 5);
    }
}
