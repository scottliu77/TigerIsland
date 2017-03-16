import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public class GlobalSettings {

    public final static Boolean defaultOffline = true;
    public final static int defaultGames = 1;
    public final static int defaultPlayers = 2;
    public final static float defaultTurnTime = 20;
    public final static String defaultIPaddress = "WheresWaldo";

    public final static int minGames = 1;
    public final static int maxGames = 10;

    public final static int minPlayers = 1;
    public final static int maxPlayers = 8;

    public final static float minTurnTime = 0;
    public final static float maxTurnTime = 100;

    public final Boolean offline;
    public final int gameCount;
    public final int playerCount;
    public final float turnTime;
    public final String IPaddress;

    private ArgumentParser parser;

    GlobalSettings() {
       this.offline = defaultOffline;
       this.gameCount = defaultGames;
       this.playerCount = defaultPlayers;
       this.turnTime = defaultTurnTime;
       this.IPaddress = defaultIPaddress;
       this.parser = ArgumentParsers.newArgumentParser("TigerIsland ArgumentParser");
    }

    GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime) throws ArgumentParserException {
        this.offline = offline;
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.IPaddress = defaultIPaddress;
        this.parser = ArgumentParsers.newArgumentParser("TigerIsland ArgumentParser");

        try {
            validateGameCount();
            validatePlayerCount();
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime, String IPaddress, ArgumentParser parser) throws ArgumentParserException {
        this.offline = offline;
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.IPaddress = IPaddress;
        this.parser = parser;

        try {
            validateGameCount();
            validatePlayerCount();
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    private void validateGameCount() throws ArgumentParserException {
        if (gameCount < minGames || gameCount > maxGames) {
            throw new ArgumentParserException("Game count must be within the range of " + minGames + " to " + maxGames + ".", parser);
        }
    }

    private void validatePlayerCount() throws ArgumentParserException {
        if (playerCount < minPlayers || playerCount > maxPlayers) {
            throw new ArgumentParserException("Player count must be within the range of " + minPlayers + " to " + maxPlayers + ".", parser);
        }
    }

    private void validateTurnTime() throws ArgumentParserException {
        if (turnTime < minTurnTime || turnTime > maxTurnTime) {
            throw new ArgumentParserException("Turn time must be within the range of " + minTurnTime + " to " + maxTurnTime + ".", parser);
        }
    }

}
