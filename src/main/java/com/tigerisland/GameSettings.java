package com.tigerisland;

import com.tigerisland.game.PlayerSet;

public class GameSettings {

    private GlobalSettings globalSettings;
    private Deck deck;
    private PlayerSet playerSet;

    public GameSettings() {
        this.globalSettings = new GlobalSettings();
    }

    public GameSettings(GlobalSettings settings) {
        this.globalSettings = settings;
    }

    public void setDeck() {
        if(globalSettings.getServerSettings().offline) {
            deck = new Deck();
        } else {
            // TODO Server implementation of deck(?)
        }
    }

    public void setPlayOrder() {
        if(globalSettings.getServerSettings().offline) {
            playerSet = new PlayerSet(globalSettings);
        } else {
            // TODO Server implementation of play order(?)
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayerSet getPlayerSet() {
        return playerSet;
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

}
