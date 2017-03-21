package com.tigerisland;

import java.util.*;

public class PlayerOrder {

    private int playerCount;
    private int currentPlayer = 0;
    private ArrayList<Player> players = new ArrayList<Player>();

    PlayerOrder(GlobalSettings globalSettings) {
        this.playerCount = globalSettings.playerCount;
        for(int player = 0; player < this.playerCount; player++) {
            players.add(player, new Player(Color.values()[player]));
        }
    }

    public void shufflePlayerOrder(){
        Collections.shuffle(players);
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
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

    public ArrayList<Player> getPlayerList() {
        return players;
    }
}
