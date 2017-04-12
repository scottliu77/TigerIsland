package com.tigerisland;

import com.tigerisland.game.Game;
import com.tigerisland.messenger.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Match {

    public final int GAMES_PER_MATCH = 2;

    protected GlobalSettings globalSettings;
    protected GameSettings newGameSettings;
    protected HashMap<String, Game> games;
    protected HashMap<String, Thread> gameThreads;

    private ArrayList<String> gameIDsAssigned;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.games = new HashMap<String, Game>();
        this.gameIDsAssigned = new ArrayList<String>();
        this.gameThreads = new HashMap<String, Thread>();
    }

    public void run() {
        startGames();
        //waitForAllGamesToEnd();
        closeGames();
        cleanupGameMessages();
    }

    private void startGames() {
        for(int game = 1; game <= GAMES_PER_MATCH; game++) {
            String gameID = configureGameSettings();
            createGameAsThread(gameID);
            gameThreads.get(gameID).start();
            System.out.println(Client.getTime() + "TIGERISLAND: GAME - " + gameID + " HAS BEGUN");
        }
    }

    private String configureGameSettings() {
        String gameID = waitForGameID();
        newGameSettings = new GameSettings(globalSettings);
        newGameSettings.setGameID(gameID);
        return gameID;
    }

    private void createGameAsThread(String gameID) {
        Game newGame = new Game(newGameSettings);
        games.put(gameID, newGame);
        gameThreads.put(gameID, new Thread(newGame));
    }

    private String waitForGameID() {
        while(true) {
            for(Message message : globalSettings.inboundQueue) {
                if(message.getGameID() != null && message.getMessageType() != null) {
                    if(gameIDsAssigned.contains(message.getGameID()) == false) {
                        gameIDsAssigned.add(message.getGameID());
                        return message.getGameID();
                    }
                }
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    protected void closeGames() {

        for(Thread gameThread : gameThreads.values()) {
            try {
                gameThread.join();
                System.out.println(Client.getTime() + "TIGERISLAND: Game - " + gameThread.getName() + " has ENDED");
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

    private void cleanupGameMessages() {
        for(Message message : globalSettings.inboundQueue) {
            if(message.getMessageType() != null) {
                if(message.getMessageType() == MessageType.MAKEMOVE) {
                    message.setProcessed();
                }
                if(message.getMessageType().getSubtype().equals("BUILDACTION")) {
                    message.setProcessed();
                }
                if(message.getMessageType() == MessageType.GAMEOVER) {
                    message.setProcessed();
                }
            }
        }
    }

}
