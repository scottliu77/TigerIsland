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

    protected Listener listener;
    protected Messenger messenger;
    protected LocalServer localServer;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.gameSettings = new GameSettings(this.globalSettings);
        this.games = new ArrayList<Game>();
        this.listener = new Listener(globalSettings);
        this.messenger = new Messenger(globalSettings);
        this.localServer = new LocalServer(globalSettings);
        setup();
    }

    private void setup() {
        gameSettings.setPlayOrder();
        if(globalSettings.getServerSettings().offline) {
            ConsoleOut.printClientMessage("Setting up an OFFLINE tournament");
        } else {
            ConsoleOut.printClientMessage("Setting up a SERVER-HOSTED tournament");
        }
        constructGames();
    }

    private void constructGames() {
        for(int game = 0; game < globalSettings.gameCount; game++) {
            ConsoleOut.printClientMessage("Game #" + game + " created");
            games.add(game, new Game(game, gameSettings));
        }
    }

    public void run() {
        if(globalSettings.getServerSettings().offline) {
            createLocalServerAndStartThreads();
        } else {
            createAndStartAllThreads();
        }
        System.exit(0);
    }

    protected void createLocalServerAndStartThreads() {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        ConsoleOut.printClientMessage("Local Server is RUNNING");

        createAndStartAllThreads();

        closeLocalServer(localServerThread);
    }

    protected void createAndStartAllThreads() {
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        ConsoleOut.printClientMessage("Listener is RUNNING");

        Thread messengerThread = new Thread(this.messenger);
        messengerThread.start();
        ConsoleOut.printClientMessage("Messager is RUNNING");

        gameThreads = new ArrayList<Thread>();
        for(Game game: games) {
            gameThreads.add(0, new Thread(game));
            gameThreads.get(0).start();
            ConsoleOut.printClientMessage("Game (thread) #" + game.getGameID() + " started");
        }

        waitForAllGamesToEnd();

        for(Thread gameThread : gameThreads) {
            try {
                gameThread.join();
                ConsoleOut.printClientMessage("Game (thread): " + gameThread.getName() + " has CLOSED");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        closeMessenger(messengerThread);
        closeListener(listenerThread);
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


    private void closeMessenger(Thread messengerThread) {
        while(messengerThread.isAlive()) {
            messengerThread.interrupt();
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ConsoleOut.printClientMessage("Messenger has CLOSED");
    }

    private void closeListener(Thread listenerThread) {
        try {
            listenerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConsoleOut.printClientMessage("Listener has CLOSED");
    }

    private void closeLocalServer(Thread localServerThread) {
        while(localServerThread.isAlive()) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ConsoleOut.printClientMessage("Local Server has CLOSED");
    }

}
