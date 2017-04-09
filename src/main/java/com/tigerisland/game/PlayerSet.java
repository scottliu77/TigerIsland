package com.tigerisland.game;

import com.tigerisland.GlobalSettings;

import java.util.*;

public class PlayerSet {

    private Player currentPlayer;
    private HashMap<String, Player> players = new HashMap<String, Player>();

    public PlayerSet(GlobalSettings globalSettings) {
        String ourPlayerID = globalSettings.getServerSettings().getPlayerID();
        Player ourPlayer = new Player(Color.WHITE, ourPlayerID, PlayerType.TOTOROLINESAI);

        //TODO Confirm target AI
        players.put(ourPlayerID, ourPlayer);

        String opponentPlayerID = globalSettings.getServerSettings().getOpponentID();
        Player opponentPlayer = new Player(Color.BLACK, opponentPlayerID, PlayerType.SAFEAI);
        players.put(opponentPlayerID, opponentPlayer);
    }

    public PlayerSet(PlayerSet playerSet) {
        this.currentPlayer = playerSet.getCurrentPlayer();
        this.players = new HashMap<String, Player>();
        for(String key: players.keySet()) {
            this.players.put(key, new Player(playerSet.getPlayer(key)));
        }
    }

    public void updatePlayerState(String playerID, Player updatedPlayer) {
        players.put(playerID, updatedPlayer);
    }

    public void incrementPlayer() {
        for(String key : players.keySet()) {
            if(!currentPlayer.equals(key)) {
                currentPlayer = players.get(key);
                return;
            }
        }
    }

    public HashMap<String, Player> getPlayerList() {
        return players;
    }

    public Player getPlayer(String playerID) {
        return players.get(playerID);
    }

    public void setCurrentPlayer(String playerID) {
        currentPlayer = players.get(playerID);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
