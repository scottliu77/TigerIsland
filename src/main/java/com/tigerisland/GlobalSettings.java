package com.tigerisland;

import com.tigerisland.messenger.Message;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalSettings {

    public final Boolean manualTesting;

    public final static int defaultGames = 1;
    public final static int defaultPlayers = 2;
    public final static float defaultTurnTime = 20;

    public final static int minGames = 0;
    public final static int maxGames = 10;

    public final static int minPlayers = 0;
    public final static int maxPlayers = 8;

    public final static float minTurnTime = 0;
    public final static float maxTurnTime = 100;

    public final int gameCount;
    public final int playerCount;
    public final float turnTime;

    private ArgumentParser parser;
    private ServerSettings serverSettings;

    public final BlockingQueue<Message> inboundQueue = new LinkedBlockingQueue<Message>();
    public final BlockingQueue<Message> outboundQueue = new LinkedBlockingQueue<Message>();
    public final BlockingQueue<Message> messagesReceived = new LinkedBlockingQueue<Message>();

    public GlobalSettings() {
       this.gameCount = defaultGames;
       this.playerCount = defaultPlayers;
       this.turnTime = defaultTurnTime;
       this.manualTesting = false;

       this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");

       this.serverSettings = new ServerSettings();
    }

    public GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime) throws ArgumentParserException {
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.manualTesting = false;

        this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");

        this.serverSettings = new ServerSettings();

        try{
            validateGameCount();
            validatePlayerCount();
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    public GlobalSettings(Boolean offline, int gameCount, int playerCount, float turnTime, String IPaddress, int port, String username, String password, Boolean manualTesting, ArgumentParser parser) throws ArgumentParserException {
        this.gameCount = gameCount;
        this.playerCount = playerCount;
        this.turnTime = turnTime;
        this.manualTesting = manualTesting;

        this.parser = parser;

        this.serverSettings = new ServerSettings(IPaddress, port, username, password, offline);

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
            throw new ArgumentParserException("com.tigerisland.game.Player count must be within the range of " + minPlayers + " to " + maxPlayers + ".", parser);
        }
    }

    private void validateTurnTime() throws ArgumentParserException {
        if (turnTime < minTurnTime || turnTime > maxTurnTime) {
            throw new ArgumentParserException("Turn time must be within the range of " + minTurnTime + " to " + maxTurnTime + ".", parser);
        }
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }
}
