package com.tigerisland;

import java.util.ArrayList;

public class Game {

    protected GameSettings gameSettings;
    protected ArrayList<Player> players;
    protected Board board;
    protected Rules rules;
    private PlayOrder playOrder;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        board = new Board();
        players = new ArrayList<Player>();
        rules = new Rules();
        for(int player = 0; player < this.gameSettings.globalSettings.playerCount; player++){
            players.add(player, new Player(Color.values()[player]));
        }
    }

    public void start() {

    }

    public boolean noValidMoves(){
        Player currentPlayer = playOrder.getCurrentPlayer();
        if(noMoreVillagers(currentPlayer) && cantPlayTotoro(currentPlayer)){
            return true;
        }
        return false;
    }

    private boolean playerIsOutOfPieces(){
        for(Player player : players){
            if(player.getPieceSet().inventoryEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean noMoreVillagers(Player player){
        return player.getPieceSet().getNumberOfVillagersRemaining() == 0;
    }

    private boolean cantPlayTotoro(Player player){
        if(player.getPieceSet().getNumberOfTotoroRemaining() == 0){
            return true;
        }
        //otherwise check board's list of settlements (to be created) and see if one exists in this player's color with
        //room for a totoro. For every large enough settlement, see if there's an adjacent hex capable of being settled

        return false;
    }
}
