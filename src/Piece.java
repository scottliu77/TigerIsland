public class Piece {

    private Color color;
    private PieceType type;

    Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public String getColorString() {
        return color.getColorString();
    }

    public PieceType getType() {
        return type;
    }

    public String getTypeString() {
        return type.getType();
    }
}
