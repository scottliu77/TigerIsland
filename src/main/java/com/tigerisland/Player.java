package com.tigerisland;

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
        this.color = player.getPlayerColor();
        this.pieceSet = new PieceSet(player.getPieceSet()); //might need clone constructor here too
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
gi