package com.tigerisland.game;

import com.tigerisland.GlobalSettings;

import java.util.*;

public class PlayerSet {

    private int currentPlayer;
    private HashMap<Integer, Player> players = new HashMap<Integer, Player>();

    public PlayerSet(GlobalSettings globalSettings) {
        int ourPlayerID = globalSettings.getServerSettings().getPlayerID();
        Player ourPlayer = new Player(Color.WHITE, ourPlayerID, PlayerType.TOTOROLINESAI);

        //TODO Confirm target AI
        players.put(ourPlayerID, ourPlayer);

        int opponentPlayerID = globalSettings.getServerSettings().getOpponentID();
        Player opponentPlayer = new Player(Color.BLACK, opponentPlayerID, PlayerType.SERVER);
        players.put(opponentPlayerID, opponentPlayer);
    }

    public PlayerSet(PlayerSet playerSet) {
        this.players = new HashMap<Integer, Player>();
        for(int key: players.keySet()) {
            this.players.put(key, new Player(playerSet.getPlayer(key)));
        }
    }

    public void updatePlayerState(int playerID, Player updatedPlayer) {
        players.put(playerID, updatedPlayer);
    }

    public void incrementPlayer() {
        for(int key : players.keySet()) {
            if(currentPlayer != key) {
                currentPlayer = key;
                return;
            }
        }
    }

    public HashMap<Integer, Player> getPlayerList() {
        return players;
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID);
    }

    public void setCurrentPlayer(int playerID) {
        currentPlayer = playerID;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }
}
