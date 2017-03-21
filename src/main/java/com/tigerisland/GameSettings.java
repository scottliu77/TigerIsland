package com.tigerisland;

public class GameSettings {

    public GlobalSettings globalSettings;
    private Deck deck;
    private PlayerOrder playerOrder;

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
            playerOrder = new PlayerOrder(globalSettings);
        } else {
            // TODO Server implementation of play order(?)
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

}
