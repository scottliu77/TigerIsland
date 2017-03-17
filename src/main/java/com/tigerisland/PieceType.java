package com.tigerisland;

public enum PieceType {

    VILLAGER("Villager"),
    TOTORO("Totoro");

    private String type;

    PieceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}