package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;

import java.util.*;

public class PlayerSet {

    private int playerCount;
    private int currentPlayer = 0;
    private ArrayList<Player> players = new ArrayList<Player>();

    public PlayerSet(GlobalSettings globalSettings) {
        for(int player = 0; player < GlobalSettings.players; player++) {
            players.add(player, new Player(Color.values()[player]));
        }
    }

    public PlayerSet(PlayerSet playerSet) {
        this.playerCount = playerSet.getPlayerList().size();
        this.currentPlayer = playerSet.currentPlayer;
        this.players = new ArrayList<Player>();
        for(Player player : playerSet.getPlayerList()) {
            this.players.add(new Player(player));
        }
    }

    public void setRandomAItypes() {
        for(Player player : players) {
            player.setPlayerType(PlayerType.pickRandomAItype());
        }
    }

    public void shufflePlayerList(){
        Collections.shuffle(players);
    }

    public void updatePlayerState(Player updatedPlayer) {
        players.set(currentPlayer, updatedPlayer);
    }

    public void setNextPlayer(){
        if (currentPlayer == playerCount - 1) {
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
