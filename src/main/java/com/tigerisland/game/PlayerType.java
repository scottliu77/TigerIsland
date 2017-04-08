package com.tigerisland.game;

import java.util.*;

public enum PlayerType {

    HUMAN("HUMAN"),
    SAFEAI("SAFEAI"),
    TOTOROLINESAI("TOTOROLINESAI"),
    RANDOMAI("RANDOMAI"),
    SERVER("SERVER"),
    JACKSAI("JACKSAI");

    private String typeString;

    PlayerType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }

    public static EnumSet<PlayerType> AITypes() {
        return EnumSet.of(SAFEAI);
    }

    public static PlayerType pickRandomAItype() {
        List<PlayerType> types = Collections.unmodifiableList(Arrays.asList(SAFEAI));
        return types.get(new Random().nextInt(AITypes().size()));
    }
}
