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

    public int getScore() {
        return score.getScore();
    }

    public void addPoints(int points) {
        score.addPoints(points);
    }

    public Color getPlayerColor() {
        return color;
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

}
