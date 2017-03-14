/**
 * Created by scott on 3/13/17.
 */
public class Player {

    private int score;
    private PlayerColor color;

    public Player(PlayerColor color) {
        this.color = color;
        score = 0;
    }

    public Player(int code) {
        switch (code) {
            case 0:
                this.color = PlayerColor.BLACK;
                break;
            case 1:
                this.color = PlayerColor.WHITE;
                break;
        }
        score = 0;
    }

    public String getPlayerColor() {
        return this.color.getColor();
    }

    public int getPlayerCode() {
        return this.color.getCode();
    }

    public int getScore() {
        return score;
    }
}
