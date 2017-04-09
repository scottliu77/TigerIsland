package com.tigerisland.game;

import java.util.*;

public enum PlayerType {

    HUMAN("HUMAN"),
    SAFEAI("SAFEAI"),
    TOTOROLINESAI("TOTOROLINESAI"),
    RANDOMAI("RANDOMAI"),
    SERVER("SERVER"),
    TIGERFORMAI("TIGERFORMAI"),
    JACKSAI("JACKSAI");

    private String typeString;

    PlayerType(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}
