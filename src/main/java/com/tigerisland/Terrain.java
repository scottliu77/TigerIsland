package com.tigerisland;

public enum Terrain {
    VOLCANO(0, "Volcano"),
    GRASSLANDS(1, "Grasslands"),
    JUNGLE(2, "Jungle"),
    LAKE(3, "Lake"),
    ROCKY(4, "Rocky");

    private String type;

    Terrain(int code, String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
