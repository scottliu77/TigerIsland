/**
 * Created by scott on 3/13/17.
 */
public enum PlayerColor {

    BLACK(0, "Black"),
    WHITE(1, "White");

    private int code;
    private String color;

    PlayerColor(int code, String color) {
        this.code = code;
        this.color = color;
    }

    public int getCode() {
        return this.code;
    }

    public String getColor() {
        return this.color;
    }
}
