package com.tigerisland;

import com.tigerisland.game.PlayerSet;
import com.tigerisland.game.PlayerType;

public class GameSettings {

    private GlobalSettings globalSettings;
    private Deck deck;
    private PlayerSet playerSet;

    public GameSettings() {
        this.globalSettings = new GlobalSettings();
    }

    public GameSettings(GlobalSettings settings) {
        this.globalSettings = settings;
        setDeck();
    }

    public GameSettings(GameSettings gameSettings) {
        this.globalSettings = gameSettings.getGlobalSettings();
        this.deck = new Deck(gameSettings.getDeck());
        this.playerSet = new PlayerSet(gameSettings.getPlayerSet());
    }

    public void setDeck() {
        deck = new Deck();
        if(globalSettings.getServerSettings().offline) {
            deck.createOfflineDeck();
        }
    }

    public void setPlayOrder() {
        if(globalSettings.getServerSettings().offline) {
            playerSet = new PlayerSet(globalSettings);
            playerSet.setRandomAItypes();
            if(globalSettings.manualTesting) {
                playerSet.getPlayerList().get(0).setPlayerType(PlayerType.HUMAN);
            }
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
