package com.tigerisland;

/**
 * Created by scott on 3/13/17.
 */
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

    public Piece createNewSettlement(){
        try {
            return pieceSet.placeVillager();
        } catch (IndexOutOfBoundsException exception){
            throw exception;
        }
    }

    public Piece expandSettlement(int villagersNeeeded){
        try {
            return pieceSet.placeMultipleVillagers(villagersNeeeded);
        } catch (IndexOutOfBoundsException exception) {
            throw exception;
        }
    }

    public Piece createTotoroSanct(){
        try {
            return pieceSet.placeTotoro();
        } catch (IndexOutOfBoundsException exception) {
            throw exception;
        }
    }

}
