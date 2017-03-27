package com.tigerisland.game;

import com.tigerisland.GlobalSettings;

import java.util.*;

public class PlayerSet {

    private int playerCount;
    private int currentPlayer = 0;
    private ArrayList<Player> players = new ArrayList<Player>();

    public PlayerSet(GlobalSettings globalSettings) {
        this.playerCount = globalSettings.playerCount;
        for(int player = 0; player < this.playerCount; player++) {
            players.add(player, new Player(Color.values()[player]));
        }
    }

    public void shufflePlayerList(){
        Collections.shuffle(players);
    }

    public void updatePlayerState(Player updatedPlayer) {
        players.set(currentPlayer, updatedPlayer);
    }

    public void setNextPlayer(){
        if (currentPlayer == playerCount) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }

}
