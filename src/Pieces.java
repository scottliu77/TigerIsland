import java.util.ArrayList;

public class Pieces {

    ArrayList<Meeple> meepleSet;
    ArrayList<Totoro> totoroSet;

    public Pieces(int ownerCode) {
        this.meepleSet = new ArrayList<Meeple>();
        this.totoroSet = new ArrayList<Totoro>();
        this.generateMeepleSet(ownerCode);
        this.generateTotoroSet(ownerCode);
    }

    private void generateMeepleSet(int code) {
        for(int pieceNumber = 1; pieceNumber <= 20; pieceNumber++) {
            this.meepleSet.add(new Meeple(code));
        }
    }

    private void generateTotoroSet(int code) {
        for(int pieceNumber = 1; pieceNumber <= 3; pieceNumber++) {
            this.totoroSet.add(new Totoro(code));
        }
    }

    public Totoro placeTotoro() {
        return this.totoroSet.remove(0);
    }

    public Meeple placeMeeple() {
        return this.meepleSet.remove(0);
    }
}
