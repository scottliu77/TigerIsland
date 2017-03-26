package com.tigerisland;

import com.tigerisland.game.PlayerList;

public class GameSettings {

    public GlobalSettings globalSettings;
    private Deck deck;
    private PlayerList playerList;

    GameSettings() {
        this.globalSettings = new GlobalSettings();
    }

    GameSettings(GlobalSettings settings) {
        this.globalSettings = settings;
    }

    public void setDeck() {
        if(globalSettings.offline) {
            deck = new Deck();
        } else {
            // TODO Server implementation of deck(?)
        }
    }

    public void setPlayOrder() {
        if(globalSettings.offline) {
            playerList = new PlayerList(globalSettings);
        } else {
            // TODO Server implementation of play order(?)
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

}
