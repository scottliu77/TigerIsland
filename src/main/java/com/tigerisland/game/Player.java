package com.tigerisland.game;

public class Player {

    private Score score;
    private Color color;
    private PieceSet pieceSet;

    public Player(Color color) {
        this.color = color;
        score = new Score();
        pieceSet = new PieceSet(color);
    }

    public Player(Player player){
        this.score = new Score(player.getScore());
        this.color = getPlayerColor();
        this.pieceSet = new PieceSet(player.getPieceSet());
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

}