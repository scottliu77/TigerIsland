package com.tigerisland.game;

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
    WEAKJACKSAI("WEAKJACKSAI");

    private String typeString;

    PlayerType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}
