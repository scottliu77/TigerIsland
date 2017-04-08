package com.tigerisland.game;

import com.tigerisland.AI.AI;
import com.tigerisland.AI.SafeAI;
import com.tigerisland.AI.TotoroLinesAI;

public class Player {

    private String playerID;

    private Score score;
    private Color color;
    private PieceSet pieceSet;
    private PlayerType playerType;
    private AI playerAI;


    public Player(Color color, String playerID, PlayerType playerType) {
        this.color = color;
        this.playerID = playerID;
        score = new Score();
        pieceSet = new PieceSet(color);

        if(playerType == PlayerType.SAFEAI)
            playerAI = new SafeAI();
        else if(playerType == PlayerType.TOTOROLINESAI)
            playerAI = new TotoroLinesAI();
        else if(playerType == PlayerType.HUMAN)
            playerAI = null; //I'm not sure how to handle this situation
    }

    public Player(Player player){
        this.score = new Score(player.getScore());
        this.color = player.getPlayerColor();
        this.playerID = player.getPlayerID();
        this.pieceSet = new PieceSet(player.getPieceSet());
        this.playerType = player.getPlayerType();

        if(this.playerType == PlayerType.SAFEAI)
            playerAI = new SafeAI();
        else if(this.playerType == PlayerType.TOTOROLINESAI)
            playerAI = new TotoroLinesAI();
        else if(this.playerType == PlayerType.HUMAN)
            playerAI = null; //I'm not sure how to handle this situation
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

        if(playerType == PlayerType.SAFEAI)
            playerAI = new SafeAI();
        else if(playerType == PlayerType.TOTOROLINESAI)
            playerAI = new TotoroLinesAI();
        else if(playerType == PlayerType.HUMAN)
            playerAI = null; //I'm not sure how to handle this situation
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