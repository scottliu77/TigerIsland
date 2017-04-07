package com.tigerisland;

import com.tigerisland.game.Game;
import com.tigerisland.messenger.*;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Match {

    protected GlobalSettings globalSettings;
    protected GameSettings gameSettings;
    protected ArrayList<Game> games;
    protected ArrayList<Thread> gameThreads;


    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.gameSettings = new GameSettings(this.globalSettings);
        this.games = new ArrayList<Game>();
        constructGames();
    }

    private void constructGames() {
        // TODO allow safe assignment of gameID via first move message
        gameSettings.setGameID("A");
        games.add(new Game(gameSettings));

        gameSettings.setGameID("B");
        games.add(new Game(gameSettings));

    }

    public void run() {
        createAndStartAllGameThreads();
    }

    protected void createAndStartAllGameThreads() {

        gameThreads = new ArrayList<Thread>();
        for(Game game: games) {
            gameThreads.add(0, new Thread(game));
            gameThreads.get(0).start();
            System.out.println("TIGERISLAND: Game (thread) #" + game.getGameID() + " is RUNNING");
        }

        waitForAllGamesToEnd();

        for(Thread gameThread : gameThreads) {
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
                for (Thread gameThread : gameThreads) {
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
