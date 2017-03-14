public enum Placeable {

    VILLAGER(0, "Villager"),
    TOTORO(1, "Totoro");

    private int code;
    private String piece;

    Placeable(int code, String piece) {
        this.code = code;
        this.piece = piece;
    }

    public int getCode() {
        return this.code;
    }

    public String getPiece() {
        return this.piece;
    }

}
