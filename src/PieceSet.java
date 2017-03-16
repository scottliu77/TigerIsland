import java.util.ArrayList;

public class PieceSet {

    ArrayList<Piece> villagerSet;
    ArrayList<Piece> totoroSet;

    public PieceSet(Color ownerColor) {
        this.villagerSet = new ArrayList<Piece>();
        this.totoroSet = new ArrayList<Piece>();
        this.generateVillagerSet(ownerColor);
        this.generateTotoroSet(ownerColor);
    }

    private void generateVillagerSet(Color color) {
        for(int pieceNumber = 1; pieceNumber <= 20; pieceNumber++) {
            this.villagerSet.add(new Piece(color, PieceType.VILLAGER));
        }
    }

    private void generateTotoroSet(Color color) {
        for(int pieceNumber = 1; pieceNumber <= 3; pieceNumber++) {
            this.totoroSet.add(new Piece(color, PieceType.TOTORO));
        }
    }

    public int getNumberOfVillagersRemaining() {
        return villagerSet.size();
    }

    public int getNumberOfTotoroRemaining() {
        return totoroSet.size();
    }

    public Piece placeVillager() {
        return villagerSet.remove(0);
    }

    public Piece placeTotoro() {
        return totoroSet.remove(0);
    }

    public boolean inventoryEmpty(){
        if(totoroSet.size() == 0 && villagerSet.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }
}
