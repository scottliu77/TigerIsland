package com.tigerisland;

public enum MoveType {
    TILEPLACEMENT("TilePlacement"),
    VILLAGECREATION("VillageCreation"),
    VILLAGEEXPANSION("VillageExpansion"),
    TOTOROPLACEMENT("TotoroPlacement");

    private String moveString;

    MoveType(String moveString) {
        this.moveString = moveString;
    }

    public String getMoveString() {
        return moveString;
    }
}
