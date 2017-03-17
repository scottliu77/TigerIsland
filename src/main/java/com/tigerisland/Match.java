package com.tigerisland;

import java.util.ArrayList;

public class Match {

    protected GlobalSettings globalSettings;
    protected GameSettings gameSettings;
    protected ArrayList<Game> games;

    Match(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.gameSettings = new GameSettings(this.globalSettings);
        this.games = new ArrayList<Game>();
        setup();
    }

    private void setup() {
        if(globalSettings.offline) {
            setupOfflineMatch();
        } else{
            setupOnlineMatch();
        }
    }

    // TODO Multi-threading required here. Otherwise games are run sequentially.
    private void setupOfflineMatch() {
        configureOfflineGameSettings();
        constructOfflineGames();
        startOfflineGames();
    }

    private void configureOfflineGameSettings() {
        gameSettings.setDeck();
        gameSettings.setPlayOrder();
    }

    private void constructOfflineGames() {
        for(int game = 0; game < globalSettings.gameCount; game++) {
            games.add(game, new Game(globalSettings));
        }
    }

    private void startOfflineGames() {
        for(Game game: games) {
            game.start();
        }
    }

    private void setupOnlineMatch() {
        // TODO server setup procedure will go here
        System.out.println("TODO - waiting for server specifications");
    }
}
