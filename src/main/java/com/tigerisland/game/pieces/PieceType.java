package com.tigerisland.game.pieces;

public enum PieceType {

    VILLAGER("Villager"),
    TOTORO("Totoro"),
    TIGER("Tiger");

    private String type;

    PieceType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }
}