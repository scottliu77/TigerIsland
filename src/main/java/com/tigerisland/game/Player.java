package com.tigerisland.game;

import com.tigerisland.AI.AI;

public class Player {

    private String playerID;

    private Score score;
    private Color color;
    private PieceSet pieceSet;
    private PlayerType playerType;
    private AI playerAI;

    public Player(Color color, String playerID) {
        this.color = color;
        this.playerID = playerID;
        score = new Score();
        pieceSet = new PieceSet(color);
    }

    public Player(Player player){
        this.score = new Score(player.getScore());
        this.color = player.getPlayerColor();
        this.playerID = player.getPlayerID();
        this.pieceSet = new PieceSet(player.getPieceSet());
        this.playerType = player.getPlayerType();
//        try {
//            this.playerAI = new AI(player.getPlayerAI());
//        } catch (NullPointerException exception) {
//            // Ignore this
//        }
    }

    public void updatePlayerState(Player updatedPlayer) {
        score = updatedPlayer.getScore();
        pieceSet = updatedPlayer.getPieceSet();
    }

    public Score getScore() {
        return score;
    }

    public Color getPlayerColor() {
        return color;
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
        this.playerAI = new AI(playerType);
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public AI getPlayerAI() {
        return playerAI;
    }

    public String getPlayerID() {
        return playerID;
    }
}