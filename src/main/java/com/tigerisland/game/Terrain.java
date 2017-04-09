package com.tigerisland.game;

public enum Terrain {

    VOLCANO("Volcano"),
    GRASSLANDS("Grasslands"),
    JUNGLE("Jungle"),
    LAKE("Lake"),
    ROCKY("Rocky");

    private String terrain;

    Terrain(String terrain) {
        this.terrain = terrain;
    }

    public String getTerrainString() {
        return terrain;
    }
}

