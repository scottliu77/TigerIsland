package com.tigerisland;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class TigerIsland {

    private ArgumentParser parser;
    private Namespace parsedArguments;
    protected GlobalSettings globalSettings;
    protected Match match;

    public TigerIsland() {}

    public void parseArguments(String[] args) throws ArgumentParserException {

        parser = ArgumentParsers.newArgumentParser("TigerParser")
                .defaultHelp(true)
                .description("Specify TigerIsland match globalSettings.");
        parser.addArgument("-o", "--offline").type(Arguments.booleanType())
                .setDefault(ServerSettings.defaultOffline)
                .help("Toggle running system in offline mode, moveProcessor v. moveProcessor");
        parser.addArgument("-g", "--games").type(Integer.class)
                .setDefault(GlobalSettings.defaultGames)
                .help("Specify the number of games to be call concurrently in each match");
        parser.addArgument("-n", "--players").type(Integer.class)
                .setDefault(GlobalSettings.defaultPlayers)
                .help("Specify the number of players in each match");
        parser.addArgument("-t", "--turnTime").type(Float.class)
                .setDefault(GlobalSettings.defaultTurnTime)
                .help("Specify the time allowed per turnState");
        parser.addArgument("-i", "--ipaddress").type(String.class)
                .setDefault(ServerSettings.defaultIPaddress)
                .help("Specify the ip address of the TigerHost server");
        parser.addArgument("-p", "--port").type(Integer.class)
                .setDefault(ServerSettings.defaultPort)
                .help("Specify the port used by the TigerHost server");
        parser.addArgument("--username").type(String.class)
                .setDefault(ServerSettings.defaultUsername)
                .help("Specify username used by the TigerHost server");
        parser.addArgument("--password").type(String.class)
                .setDefault(ServerSettings.defaultPassword)
                .help("Specify password used by the TigerHost server");

        parsedArguments = parser.parseArgs(args);

        Boolean offline = parsedArguments.get("offline");
        int gameCount = parsedArguments.get("games");
        int playerCount = parsedArguments.get("players");
        float turnTime = parsedArguments.get("turnTime");
        String ipaddress = parsedArguments.get("ipaddress");
        int port = parsedArguments.get("port");
        String username = parsedArguments.get("username");
        String password = parsedArguments.get("password");

        try {
            this.globalSettings = new GlobalSettings(offline, gameCount, playerCount, turnTime, ipaddress, port, username, password, parser);
        } catch (ArgumentParserException exception) {
            throw exception;
        }

        match = new Match(globalSettings);
    }

    private void run() {
        match.run();
    }

    public Match getMatch() {
        return match;
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


