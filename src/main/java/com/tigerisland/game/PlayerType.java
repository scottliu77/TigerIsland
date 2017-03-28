package com.tigerisland.game;

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

}
