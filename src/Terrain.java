public enum Terrain {
    VOLCANO(0, "Volcano"),
    GRASSLANDS(1, "Grasslands"),
    JUNGLE(2, "Jungle"),
    LAKE(3, "Lake"),
    ROCKY(4, "Rocky");

    private int code;
    private String type;

    Terrain(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

}
