package com.tigerisland;

public enum BuildActionType {
    VILLAGECREATION("VillageCreation"),
    VILLAGEEXPANSION("VillageExpansion"),
    TOTOROPLACEMENT("TotoroPlacement"),
    TIGERPLACEMENT("TigerPlacement");

    private String moveString;

    BuildActionType(String moveString) {
        this.moveString = moveString;
    }

    public String getMoveString() {
        return moveString;
    }
}
