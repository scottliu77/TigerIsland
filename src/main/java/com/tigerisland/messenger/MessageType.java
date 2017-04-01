package com.tigerisland.messenger;

import java.util.EnumSet;

public enum  MessageType {

    AUTH("AUTH"),
    FOUNDSETTLEMENT("BUILDACTION"),
    EXPANDSETTLEMENT("BUILDACTION"),
    BUILDTOTORO("BUILDACTION"),
    BUILDTIGER("BUILDACTION"),
    PROCESSED("STATUS");

    private String subtype;

    MessageType(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }

    public static EnumSet<MessageType> buildActions() {
        return EnumSet.of(FOUNDSETTLEMENT, EXPANDSETTLEMENT, BUILDTOTORO, BUILDTIGER);
    }
}
