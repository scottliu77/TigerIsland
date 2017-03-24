package com.tigerisland;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalSettings {

    public final static String END_CODE = "END";

    public final static Boolean defaultOffline = true;
    public final static int defaultGames = 1;
    public final static int defaultPlayers = 2;
    public final static float defaultTurnTime = 20;

    public final static String defaultIPaddress = "localhost";
    public final static int defaultPort = 6539;

    public final static String defaultUsername = "username";
    public final static String defaultPassword = "password";

    public final static int minGames = 0;
    public final static int maxGames = 10;

    public final static int minPlayers = 0;
    public final static int maxPlayers = 8;

    public final static float minTurnTime = 0;
    public final static float maxTurnTime = 100;

    public final Boolean offline;
    public final int gameCount;
    public final int playerCount;
    public final float turnTime;
    public final String IPaddress;
    public final int port;
    public final String username;
    public final String password;

    public static Boolean localServerRunning;

    private ArgumentParser parser;

    public final BlockingQueue<String> inboundQueue = new LinkedBlockingQueue<String>();
    public final BlockingQueue<String> outboundQueue = new LinkedBlockingQueue<String>();
    public final BlockingQueue<String> messagesReceived = new LinkedBlockingQueue<String>();

    public GlobalSettings() {
       this.offline = defaultOffline;
       this.localServerRunning = defaultOffline;
       this.gameCount = defaultGames;
       this.playerCount = defaultPlayers;
       this.turnTime = defaultTurnTime;
       this.IPaddress = defaultIPaddress;
       this.port = defaultPort;
       this.username = defaultUsername;
       this.password = defaultPassword;
       this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");
    }

    public GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime) throws ArgumentParserException {
        this.offline = offline;
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.IPaddress = defaultIPaddress;
        this.port = defaultPort;
        this.username = defaultUsername;
        this.password = defaultPassword;
        this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");

        this.localServerRunning = offline;

        try {
            validateGameCount();
            validatePlayerCount();
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    public GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime, String IPaddress, int port, String username, String password, ArgumentParser parser) throws ArgumentParserException {
        this.offline = offline;
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.IPaddress = IPaddress;
        this.port = port;
        this.parser = parser;
        this.username = username;
        this.password = password;

        this.localServerRunning = offline;

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
            throw new ArgumentParserException("com.tigerisland.Player count must be within the range of " + minPlayers + " to " + maxPlayers + ".", parser);
        }
    }

    private void validateTurnTime() throws ArgumentParserException {
        if (turnTime < minTurnTime || turnTime > maxTurnTime) {
            throw new ArgumentParserException("Turn time must be within the range of " + minTurnTime + " to " + maxTurnTime + ".", parser);
        }
    }

}
