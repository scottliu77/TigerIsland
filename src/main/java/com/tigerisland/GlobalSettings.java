package com.tigerisland;

import com.tigerisland.messenger.Message;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalSettings {

    public final Boolean manualTesting;

    public final static int games = 2;
    public final static int players = 2;

    public final static float defaultTurnTime = 20;

    public final static float minTurnTime = 0;
    public final static float maxTurnTime = 100;

    public final float turnTime;

    private ArgumentParser parser;
    private ServerSettings serverSettings;

    public final BlockingQueue<Message> inboundQueue = new LinkedBlockingQueue<Message>();
    public final BlockingQueue<Message> outboundQueue = new LinkedBlockingQueue<Message>();
    public final BlockingQueue<Message> messagesReceived = new LinkedBlockingQueue<Message>();

    public GlobalSettings() {
       this.turnTime = defaultTurnTime;
       this.manualTesting = false;

       this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");

       this.serverSettings = new ServerSettings();
    }

    public GlobalSettings(Boolean offline, float turnTime) throws ArgumentParserException {
        this.turnTime = turnTime;
        this.manualTesting = false;

        this.parser = ArgumentParsers.newArgumentParser("com.tigerisland.TigerIsland ArgumentParser");

        this.serverSettings = new ServerSettings();

        try{
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
        }
    }

    public GlobalSettings(Boolean offline, float turnTime, String IPaddress, int port, String tournamentPassword, String username, String password, Boolean manualTesting, ArgumentParser parser) throws ArgumentParserException {
        this.turnTime = turnTime;
        this.manualTesting = manualTesting;

        this.parser = parser;

        this.serverSettings = new ServerSettings(IPaddress, port, tournamentPassword, username, password, offline);

        try {
            validateTurnTime();
        } catch (ArgumentParserException exception) {
            throw exception;
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
