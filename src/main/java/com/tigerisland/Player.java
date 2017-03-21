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
