package com.tigerisland;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TigerIsland {

    private ArgumentParser parser;
    private Namespace parsedArguments;
    protected GlobalSettings globalSettings;
    protected Tournament tournament;

    public TigerIsland() {}

    public void parseArguments(String[] args) throws ArgumentParserException {

        parser = ArgumentParsers.newArgumentParser("TigerParser")
                .defaultHelp(true)
                .description("Specify TigerIsland match globalSettings.");
        parser.addArgument("-o", "--offline").type(Arguments.booleanType())
                .setDefault(ServerSettings.defaultOffline)
                .help("Toggle running system in offline mode, AI v. AI");
        parser.addArgument("-t", "--turnTime").type(Float.class)
                .setDefault(GlobalSettings.defaultTurnTime)
                .help("Specify the time allowed per turnState");
        parser.addArgument("-i", "--ipaddress").type(String.class)
                .setDefault(ServerSettings.defaultIPaddress)
                .help("Specify the ip address of the TigerHost server");
        parser.addArgument("-p", "--port").type(Integer.class)
                .setDefault(ServerSettings.defaultPort)
                .help("Specify the port used by the TigerHost server");
        parser.addArgument("--tournamentPassword").type(String.class)
                .setDefault(ServerSettings.defaultTournamentPassword)
                .help("Specify tournament password");
        parser.addArgument("--username").type(String.class)
                .setDefault(ServerSettings.defaultUsername)
                .help("Specify username used by the TigerHost server");
        parser.addArgument("--password").type(String.class)
                .setDefault(ServerSettings.defaultPassword)
                .help("Specify password used by the TigerHost server");
        parser.addArgument("-m", "--manual").type(Arguments.booleanType())
                .setDefault(false)
                .help("Run the system with Player 1 as a human user");
        parser.addArgument("-d", "--dummyFeed").type(Arguments.booleanType())
                .setDefault(false)
                .help("Run with dummy values in the inbound feed");
        parser.addArgument("-a", "--aiType").type(String.class)
                .setDefault("JACKSAI_V2")
                .help("Select AI type from choices: 'HUMAN', 'SAFEAI', 'JACKSAI(_V2)', 'TOTOROLINESAI(_V2)', 'RANDOMAI', and 'TIGERFORMAI'");

        parsedArguments = parser.parseArgs(args);

        Boolean offline = parsedArguments.get("offline");
        float turnTime = parsedArguments.get("turnTime");
        String ipaddress = parsedArguments.get("ipaddress");
        int port = parsedArguments.get("port");
        String tournamentPassword = parsedArguments.get("tournamentPassword");
        String username = parsedArguments.get("username");
        String password = parsedArguments.get("password");
        Boolean manualTesting = parsedArguments.get("manual");
        Boolean dummyFeed = parsedArguments.get("dummyFeed");
        String aiType = parsedArguments.get("aiType");

        try {
            this.globalSettings = new GlobalSettings(offline, turnTime, ipaddress, port, tournamentPassword, username, password, manualTesting, dummyFeed, aiType, parser);
        } catch (ArgumentParserException exception) {
            throw exception;
        }

        tournament = new Tournament(globalSettings);
    }

    private void run() {
        System.out.println("CLIENT: Starting TigerIsland tournament");
        tournament.run();
    }

    public Tournament getMatch() {
        return tournament;
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
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


