package com.tigerisland.game.player;

import java.util.*;

public enum PlayerType {

    HUMAN("HUMAN"),
    SAFEAI("SAFEAI"),
    TOTOROLINESAI("TOTOROLINESAI"),
    TOTOROLINESAI_V2("TOTOROLINESAI_V2"),
    RANDOMAI("RANDOMAI"),
    SERVER("SERVER"),
    TIGERFORMAI("TIGERFORMAI"),
    JACKSAI("JACKSAI"),
    JACKSAI_V2("JACKSAI_V2"),
    JACKSAI_V3("JACKSAI_V3"),

    JACKSAI_V4("JACKSAI_V4"),
    JACKSAI_V5("JACKSAI_V5"),
    JACKSAI_V6("JACKSAI_V6"),

    DAXSAI("DaxsAI");


    private String typeString;

    PlayerType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}
