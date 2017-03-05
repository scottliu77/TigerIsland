public class Meeple {

    private MeepleColor color;

    public Meeple(MeepleColor color) {
        this.color = color;
    }

    public Meeple(int code) {
        switch (code) {
            case 0:
                this.color = MeepleColor.BLACK;
                break;
            case 1:
                this.color = MeepleColor.WHITE;
                break;
        }
    }

    public String getMeepleColor() {
        return this.color.getColor();
    }

    public int getMeepleCode() {
        return this.color.getCode();
    }
}
