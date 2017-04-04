package com.tigerisland;

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
    String[] argsNegativeGames = new String[]{"-g", "-1"};
    String[] argsOneHundredGames = new String[]{"-g", "100"};
    String[] argsThreePlayers = new String[]{"-n", "3"};
    String[] argsRealisticExample = new String[]{"--ipaddress", "129.68.1.88", "--port", "3333", "--username", "username", "--password", "password"};

    @Before
    public void createTigerIsland() {
        this.tigerIsland = new TigerIsland();
    }

    @Test
    public void testCanCreateTigerIsland() {
        assertTrue(tigerIsland != null);
    }

    @Test
    public void testCanStartTigerIslandDefault() {
        try {
            tigerIsland.parseArguments(argsDefault);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.getServerSettings().offline == true);
    }

//    @Test
//    public void testCanStartTigerIslandOnline() {
//        try {
//            tigerIsland.parseArguments(argsOnline);
//        } catch (ArgumentParserException e) {
//            e.printStackTrace();
//        }
//        assertTrue(tigerIsland.globalSettings.offline == false);
//    }

    @Test
    public void testCanStartTigerIslandOffline() {
        try {
            tigerIsland.parseArguments(argsOffline);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.getServerSettings().offline == true);

    }

    @Test
    public void testCanStartTigerIslandOfflineLong() {
        try {
            tigerIsland.parseArguments(argsOfflineLong);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIsland.globalSettings.getServerSettings().offline == true);
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
    public void testCannotCreateMatchWithNegativeGames() {
        try {
            tigerIsland.parseArguments(argsNegativeGames);
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
    public void testCanCreateMatchUsingRealisticInput() {
        try {
            tigerIsland.parseArguments(argsRealisticExample);
            assertTrue(tigerIsland.globalSettings.getServerSettings().username.equals("username"));
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
    }

    @Test
    public void testCanSetManualInputFlag() {
        try {
            tigerIsland.parseArguments(new String[]{"--manual", "true"});
            assertTrue(tigerIsland.globalSettings.manualTesting == true);
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
    }
}
