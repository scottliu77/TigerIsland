package com.tigerisland;

import com.tigerisland.game.PlayerSet;
import com.tigerisland.game.PlayerType;

public class GameSettings {

    private GlobalSettings globalSettings;
    private Deck deck;
    private PlayerSet playerSet;
    private String gameID;

    public GameSettings() {
        this.globalSettings = setPlayerIDsIfNull(new GlobalSettings());
        this.playerSet = new PlayerSet(globalSettings);
        this.gameID = "A";
        setPlayOrder();
    }

    public GameSettings(GlobalSettings settings) {
        this.globalSettings = setPlayerIDsIfNull(settings);
        this.playerSet = new PlayerSet(globalSettings);
        this.gameID = "A";
        setPlayOrder();
        setDeck();
    }

    public GameSettings(GameSettings gameSettings) {
        this.globalSettings = gameSettings.getGlobalSettings();
        this.deck = new Deck(gameSettings.getDeck());
        this.playerSet = new PlayerSet(gameSettings.getPlayerSet());
        this.gameID = "A";
    }

    private GlobalSettings setPlayerIDsIfNull(GlobalSettings globalSettings) {
        if(globalSettings.getServerSettings().getPlayerID() == null) {
            globalSettings.getServerSettings().setPlayerID(1);
        }
        if(globalSettings.getServerSettings().getOpponentID() == null) {
            globalSettings.getServerSettings().setOpponentID(2);
        }
        return globalSettings;
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

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }
}
