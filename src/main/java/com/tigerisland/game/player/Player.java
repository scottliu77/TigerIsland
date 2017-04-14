package com.tigerisland.game.player;

import com.tigerisland.AI.*;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.pieces.PieceSet;

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
        this.playerType = playerType;

        if(playerType == PlayerType.SAFEAI)
            playerAI = new SafeAI();
        else if(playerType == PlayerType.TOTOROLINESAI)
            playerAI = new TotoroLinesAI();
        else if(playerType == PlayerType.TOTOROLINESAI_V2)
            playerAI = new TotoroLinesAI_V2();
        else if(playerType == PlayerType.RANDOMAI)
            playerAI = new RandomAI();
        else if(playerType == PlayerType.HUMAN)
            playerAI = new HumanInput();
        else if(playerType == PlayerType.JACKSAI)
            playerAI = new JacksAI();
        else if(playerType == PlayerType.TIGERFORMAI)
            playerAI = new TigerFormAI();
        else if(playerType == PlayerType.JACKSAI_V2)
            playerAI = new JacksAI_v2();
        else if(playerType == PlayerType.JACKSAI_V3)
            playerAI = new JacksAI_v3();
        else if(playerType == PlayerType.JACKSAI_V4)
            playerAI = new JacksAI_v4();
        else if(playerType == PlayerType.JACKSAI_V5)
            playerAI = new JacksAI_v5();
        else if(playerType == PlayerType.JACKSAI_V6)
            playerAI = new JacksAI_v6();

        else if(playerType == PlayerType.DAXSAI)
            playerAI = new DAXSAI();
    }

    public Player(Player player){
        this.score = new Score(player.getScore());
        this.color = player.getPlayerColor();
        this.playerID = player.getPlayerID();
        this.pieceSet = new PieceSet(player.getPieceSet());
        this.playerType = player.getPlayerType();

        playerAI = player.getPlayerAI();

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