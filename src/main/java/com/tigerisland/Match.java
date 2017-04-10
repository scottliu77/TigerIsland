package com.tigerisland;

import com.tigerisland.game.Game;
import com.tigerisland.messenger.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Match {

    protected GlobalSettings globalSettings;
    protected GameSettings gameSettingsOne;
    protected GameSettings gameSettingsTwo;
    protected HashMap<String, Game> games;
    protected HashMap<String, Thread> gameThreads;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.games = new HashMap<String, Game>();
        cleanupOldGames();
        constructGames();
    }

    private void cleanupOldGames() {
        for(Message message : globalSettings.inboundQueue) {
            if(message.getMessageType() != null) {
                if(message.getMessageType() == MessageType.MAKEMOVE) {
                    message.setProcessed();
                }
                if(message.getMessageType().getSubtype().equals("BUILDACTION")) {
                    message.setProcessed();
                }
            }
        }
    }

    private void constructGames() {
        // TODO allow safe assignment of gameID via first move message
        gameSettingsOne = new GameSettings(globalSettings);
        gameSettingsOne.setGameID("A");
        games.put(gameSettingsOne.getGameID(), new Game(gameSettingsOne));

        if(!gameSettingsOne.getGlobalSettings().manualTesting) {
            gameSettingsTwo = new GameSettings(globalSettings);
            gameSettingsTwo.setGameID("B");
            games.put(gameSettingsTwo.getGameID(), new Game(gameSettingsTwo));
        }
    }

    public void run() {
        createAndStartAllGameThreads();
    }

    protected void createAndStartAllGameThreads() {

        gameThreads = new HashMap<String, Thread>();
        for(Game game: games.values()) {
            gameThreads.put(game.gameID, new Thread(game));
            gameThreads.get(game.gameID).start();
            System.out.println("TIGERISLAND: Game (thread) #" + game.getGameID() + " is RUNNING");
        }

        waitForAllGamesToEnd();

        for(Thread gameThread : gameThreads.values()) {
            try {
                gameThread.join();
                System.out.println("TIGERISLAND: Game (thread): " + gameThread.getName() + " has CLOSED");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void waitForAllGamesToEnd() {
        try {
            while(true) {
                sleep(5);
                int gamesRunning = 0;
                for (Thread gameThread : gameThreads.values()) {
                    if (gameThread.isAlive()) {
                        gamesRunning++;
                    }
                }
                if (gamesRunning == 0) {
                    return;
                }
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
