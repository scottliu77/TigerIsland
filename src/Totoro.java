public class Totoro{

    private TotoroColor color;

    public Totoro(TotoroColor color) {
        this.color = color;
    }

    public Totoro(int code) {
        switch (code) {
            case 0:
                this.color = TotoroColor.BLUE;
                break;
            case 1:
                this.color = TotoroColor.BROWN;
                break;
        }
    }

    public String getTotoroColor() {
        return this.color.getColor();
    }

    public int getTotoroCode() {
        return this.color.getCode();
    }
}