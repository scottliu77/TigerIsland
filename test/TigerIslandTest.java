import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TigerIslandTest {

    private TigerIsland tigerIsland;
    private String[] argsDefault;
    private String[] argsOnline;
    private String[] argsOffline;
    private String[] argsOfflineLong;
    private String[] argsBadEntry;
    private String[] argsBadEntryPartial;

    @Before
    public void createTigerIsland() {
        this.tigerIsland = new TigerIsland();
        this.argsDefault = new String[]{};
        this.argsOnline = new String[]{"-o", "false"};
        this.argsOffline = new String[]{"-o", "true"};
        this.argsOfflineLong = new String[]{"--offline", "true"};
        this.argsBadEntry = new String[]{"alaka;skjfa"};
        this.argsBadEntryPartial = new String[]{"-o", "asdfkajs;ldk"};
    }

    @Test
    public void testCanCreateTigerIsland() {
        assertTrue(this.tigerIsland != null);
    }

    // TODO refactor once run method is developed
    @Test
    public void testCanRunTigerIsland() {
        assertTrue(this.tigerIsland.offline == false);
    }

    @Test
    public void testCanRunTigerIslandOnline() {
        TigerIsland tigerIslandOnline = new TigerIsland();
        try {
            tigerIslandOnline.parseArguments(this.argsOnline);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIslandOnline.offline == false);
    }

    @Test
    public void testCanRunTigerIslandOffline() {
        TigerIsland tigerIslandOffline = new TigerIsland();
        try {
            tigerIslandOffline.parseArguments(this.argsOffline);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIslandOffline.offline == true);

    }

    @Test
    public void testCanRunTigerIslandOfflineLong() {
        TigerIsland tigerIslandOffline = new TigerIsland();
        try {
            tigerIslandOffline.parseArguments(this.argsOfflineLong);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(tigerIslandOffline.offline == true);
    }

    @Test
    public void testCannotRunTigerIslandWithBadEntry() {
        try {
            TigerIsland tigerIslandBadEntry = new TigerIsland();
            tigerIslandBadEntry.parseArguments(this.argsBadEntry);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotRunTigerIslandWithPartialBadEntry() {
        try {
            TigerIsland tigerIslandBadPartialEntry = new TigerIsland();
            tigerIslandBadPartialEntry.parseArguments(this.argsBadEntryPartial);
            assertTrue(false);
        } catch (ArgumentParserException exception) {
            assertTrue(true);
        }
    }
}
