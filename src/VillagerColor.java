public enum VillagerColor {

    BLACK(0, "Black"),
    WHITE(1, "White");

    private int code;
    private String color;


    VillagerColor(int code, String color) {
        this.code = code;
        this.color = color;
    }

    public int getCode() {
        return code = code;
    }

    public String getColor() {
        return color;
    }

}
