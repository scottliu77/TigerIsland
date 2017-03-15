import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SettingsTest {

    private Settings settings;
    private ArgumentParser parser = ArgumentParsers.newArgumentParser("TigerParser");

    @Before
    public void createSettings() {
        this.settings = new Settings();
    }

    @Test
    public void testCanCreateSettings() {
        assertTrue(settings != null);
    }

    @Test
    public void testCanCreateSettingsWithFullConstructor() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.defaultGames, Settings.defaultPlayers, Settings.defaultTurnTime, "ABCD", parser);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(settings != null);

    }
    @Test
    public void testCanGetOfflineStatus() {
        assertTrue(settings.offline);
    }

    @Test
    public void testCannotSetTooFewPlayers() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.defaultGames, Settings.minPlayers - 1, Settings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooManyPlayers() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.defaultGames, Settings.maxPlayers + 1, Settings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooFewGames() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.minGames - 1, Settings.defaultPlayers, Settings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooManyGames() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.maxGames + 1, Settings.defaultPlayers, Settings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooShortOfRounds() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.defaultGames, Settings.defaultPlayers, Settings.minTurnTime - 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooLongOfRounds() {
        try {
            Settings settings = new Settings(Settings.defaultOffline, Settings.defaultGames, Settings.defaultPlayers, Settings.maxTurnTime + 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanGetNumberOfGamesInEachMatch() {
        assertTrue(settings.gameCount == Settings.defaultGames);
    }

    @Test
    public void testCanGetNumberOfPlayersInEachMatch() {
        assertTrue(settings.playerCount == Settings.defaultPlayers);
    }

    @Test
    public void testCanGetAllowedTimePerTurn() {
        assertTrue(settings.turnTime == Settings.defaultTurnTime);
    }
}


