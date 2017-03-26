package com.tigerisland.game;

public enum Terrain {

    VOLCANO("Volcano"),
    GRASSLANDS("Grasslands"),
    JUNGLE("Jungle"),
    LAKE("Lake"),
    ROCKY("Rocky");

    private String type;

    Terrain(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
