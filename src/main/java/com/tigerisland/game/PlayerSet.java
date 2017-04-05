package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;

import java.util.*;

public class PlayerSet {

    private int currentPlayer;
    private HashMap<Integer, Player> players = new HashMap<Integer, Player>();

    public PlayerSet(GlobalSettings globalSettings) {
        int ourPlayerID = globalSettings.getServerSettings().getPlayerID();
        Player ourPlayer = new Player(Color.WHITE, ourPlayerID);

        //TODO Confirm target AI
        ourPlayer.setPlayerType(PlayerType.BasicAI);
        players.put(ourPlayerID, ourPlayer);

        int opponentPlayerID = globalSettings.getServerSettings().getOpponentID();
        Player opponentPlayer = new Player(Color.BLACK, opponentPlayerID);
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
