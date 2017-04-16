package com.tigerisland.game.board;

public enum Terrain {

    VOLCANO("Volcano"),
    GRASS("Grass"),
    JUNGLE("Jungle"),
    LAKE("Lake"),
    ROCK("Rock"),
    PADDY("Paddy");

    private String terrain;

    Terrain(String terrain) {
        this.terrain = terrain;
    }

    public String getTerrainString() {
        return terrain;
    }
}

