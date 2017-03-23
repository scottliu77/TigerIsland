package com.tigerisland;

import java.util.ArrayList;

public class Match {

    protected GlobalSettings globalSettings;
    protected GameSettings gameSettings;
    protected ArrayList<Game> games;
    protected Listener listener;
    protected Messager messager;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.gameSettings = new GameSettings(this.globalSettings);
        this.games = new ArrayList<Game>();
        this.listener = new Listener(globalSettings);
        this.messager = new Messager(globalSettings);
        setup();
    }

    public void createAndStartAllThreads() {
        Thread listenerThread = new Thread(listener);
        listenerThread.start();

        Thread messagerThread = new Thread(messager);
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

    private void setup() {
        if(globalSettings.offline) {
            setupOfflineMatch();
        } else{
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

    private void setupOnlineMatch() {
        // TODO server setup procedure will go here
        System.out.println("TODO - waiting for server specifications");
    }
}
