package com.tigerisland;

import com.tigerisland.messenger.*;

import java.util.ArrayList;

public class Match {

    protected GlobalSettings globalSettings;
    protected GameSettings gameSettings;
    protected ArrayList<Game> games;
    protected Listener listener;
    protected Messenger messenger;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.gameSettings = new GameSettings(this.globalSettings);
        this.games = new ArrayList<Game>();
        this.listener = new Listener(globalSettings);
        this.messenger = new Messenger(globalSettings);
        setup();
    }

    private void setup() {
        if(globalSettings.offline) {
            setupOfflineMatch();
        } else {
            setupOnlineMatch();
        }
    }

    private void setupOfflineMatch() {
        configureOfflineGameSettings();
        constructOfflineGames();
    }

    private void configureOfflineGameSettings() {
        gameSettings.setDeck();
        gameSettings.setPlayOrder();
    }

    private void constructOfflineGames() {
        for(int game = 0; game < globalSettings.gameCount; game++) {
            games.add(game, new Game(gameSettings));
        }
    }

    public void run() {
        if(globalSettings.offline) {
            createLocalServerAndStartThreads();
        } else {
            createAndStartAllThreads();
        }
    }

    protected void createLocalServerAndStartThreads() {

        LocalServer localServer = new LocalServer(globalSettings);
        Thread localServerThread = new Thread(localServer);

        createAndStartAllThreads();

        localServerThread.interrupt();
        try {
            localServerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createAndStartAllThreads() {
        Thread listenerThread = new Thread(listener);
        listenerThread.start();

        Thread messagerThread = new Thread(messenger);
        messagerThread.start();

        ArrayList<Thread> gameThreads = new ArrayList<Thread>();
        for(Game game: games) {
            gameThreads.add(0, new Thread(game));
            gameThreads.get(0).start();
        }

        for(Thread gameThread : gameThreads) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        listenerThread.interrupt();
        messagerThread.interrupt();

        try {
            listenerThread.join();
            messagerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setupOnlineMatch() {
        configureOfflineGameSettings(); // temporary usage
        // TODO constructOnlineGames()
    }

}
