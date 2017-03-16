import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TigerIsland {

    private ArgumentParser parser;
    private Namespace parsedArguments;
    protected GlobalSettings globalSettings;

    public TigerIsland() {}

    protected void parseArguments(String[] args) throws ArgumentParserException {

        parser = ArgumentParsers.newArgumentParser("TigerParser")
                .defaultHelp(true)
                .description("Specify TigerIsland match globalSettings.");
        parser.addArgument("-o", "--offline").type(Arguments.booleanType())
                .setDefault(GlobalSettings.defaultOffline)
                .help("Toggle running system in offline mode, AI v. AI");
        parser.addArgument("-g", "--games").type(Integer.class)
                .setDefault(GlobalSettings.defaultGames)
                .help("Specify the number of games to be run concurrently in each match");
        parser.addArgument("-p", "--players").type(Integer.class)
                .setDefault(GlobalSettings.defaultPlayers)
                .help("Specify the number of players in each match");
        parser.addArgument("-t", "--turnTime").type(Float.class)
                .setDefault(GlobalSettings.defaultTurnTime)
                .help("Specify the time allowed per turn");
        parser.addArgument("-i", "--ipaddress").type(String.class)
                .setDefault(GlobalSettings.defaultIPaddress)
                .help("Specify the ip address of the TigerHost server");

        parsedArguments = parser.parseArgs(args);

        Boolean offline = parsedArguments.get("offline");
        int gameCount = parsedArguments.get("games");
        int playerCount = parsedArguments.get("players");
        float turnTime = parsedArguments.get("turnTime");
        String ipaddress = parsedArguments.get("ipaddress");

        try {
            this.globalSettings = new GlobalSettings(offline, gameCount, playerCount, turnTime, ipaddress, parser);
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    private void run() {
        Match match = new Match(globalSettings);
    }

    public static void main(String[] args) throws Exception {

        TigerIsland tigerIsland = new TigerIsland();

        try {
            tigerIsland.parseArguments(args);
        } catch (ArgumentParserException exception) {
            System.out.println(exception);
            return;
        }

        tigerIsland.run();

    }

}


