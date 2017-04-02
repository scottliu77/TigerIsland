package com.tigerisland.game;

import java.util.*;

public enum PlayerType {

    HUMAN("Human"),
    BasicAI("BasicAI"),
    SERVER("Server");

    private String typeString;

    PlayerType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }

    public static EnumSet<PlayerType> AITypes() {
        return EnumSet.of(BasicAI);
    }

    public static PlayerType pickRandomAItype() {
        List<PlayerType> types = Collections.unmodifiableList(Arrays.asList(BasicAI));
        return types.get(new Random().nextInt(AITypes().size()));
    }
}
