package com.tigerisland.game;

public enum Terrain {

    VOLCANO("Volcano"),
    GRASS("Grass"),
    JUNGLE("Jungle"),
    LAKE("Lake"),
    ROCK("Rock");

    private String terrain;

    Terrain(String terrain) {
        this.terrain = terrain;
    }

    public String getTerrainString() {
        return terrain;
    }
}

