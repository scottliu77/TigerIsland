package com.tigerisland.game.moves;

public enum BuildActionType {
    VILLAGECREATION("VillageCreation"),
    SHAMANCREATION("ShamanCreation"),
    VILLAGEEXPANSION("VillageExpansion"),
    TOTOROPLACEMENT("TotoroPlacement"),
    TIGERPLACEMENT("TigerPlacement");

    private String moveString;

    BuildActionType(String moveString) {
        this.moveString = moveString;
    }

    public String getBuildString() {
        return moveString;
    }
}
