import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TigerIslandTest {

    private TigerIsland tigerIsland;

    String[] argsDefault = new String[]{};
    String[] argsOnline = new String[]{"-o", "false"};
    String[] argsOffline = new String[]{"-o", "true"};
    String[] argsOfflineLong = new String[]{"--offline", "true"};
    String[] argsBadEntry = new String[]{"alaka;skjfa"};
    String[] argsBadEntryPartial = new String[]{"-o", "asdfkajs;ldk"};
    String[] argsThreeGames = new String[]{"-g", "3"};
    String[] argsZeroGames = new String[]{"-g", "0"};
    String[] argsOneHundredGames = new String[]{"-g", "100"};
    String[] argsThreePlayers = new String[]{"-p", "3"};

    @Before
    public void createTigerIsland() {
        this.tigerIsland = new TigerIsland();
    }

    @Test
    public void testCanCreateTigerIsland() {
        assertTrue(tigerIsland != null);
    }

    // TODO refactor once run method is developed

    @Test
    public void testCanStartTigerIslandDefault() {
        try {
            tigerIsland.parseArguments(this.argsDefault);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.offline == true);
    }

    @Test
    public void testCanStartTigerIslandOnline() {
        try {
            tigerIsland.parseArguments(argsOnline);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.offline == false);
    }

    @Test
    public void testCanStartTigerIslandOffline() {
        try {
            tigerIsland.parseArguments(argsOffline);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.offline == true);

    }

    @Test
    public void testCanStartTigerIslandOfflineLong() {
        try {
            tigerIsland.parseArguments(argsOfflineLong);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.offline == true);
    }

    @Test
    public void testCannotStartTigerIslandWithBadEntry() {
        try {
            tigerIsland.parseArguments(argsBadEntry);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotStartTigerIslandWithPartialBadEntry() {
        try {
            tigerIsland.parseArguments(argsBadEntryPartial);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanCreateMatchWithThreeGames() {
        try {
            tigerIsland.parseArguments(argsThreeGames);
            assertTrue(tigerIsland.globalSettings.gameCount == 3);
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
    }

    @Test
    public void testCannotCreateMatchWithZeroGames() {
        try {
            tigerIsland.parseArguments(argsZeroGames);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotCreateMatchWithOneHundredGames() {
        try {
            tigerIsland.parseArguments(argsOneHundredGames);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanCreateMatchWithThreePlayers() {
        try {
            tigerIsland.parseArguments(argsThreePlayers);
            assertTrue(tigerIsland.globalSettings.playerCount == 3);
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
    }
}
