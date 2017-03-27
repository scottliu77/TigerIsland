package com.tigerisland.messenger;

public enum  MessageType {

    AUTH("AUTH"),
    TILEPLACEMENT("TILEPLACEMENT"),
    VILLAGECREATION("BUILDACTION"),
    VILLAGEXPANSION("BUILDACTION"),
    TOTOROPLACEMENT("BUILDACTION"),
    TIGERPLACEMENT("BUILDACTION"),
    PROCESSED("STATUS");

    private String subtype;

    MessageType(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }
}
